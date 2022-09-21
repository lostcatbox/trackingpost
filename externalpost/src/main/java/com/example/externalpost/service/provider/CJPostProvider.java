package com.example.externalpost.service.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.PostCompanyEnum;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CJPostProvider implements PostProvider {
    @Override
    public PostDto get(String postNumber) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> resultMap = new HashMap<>();
        try {
            Connection.Response responseget = Jsoup.connect("https://www.cjlogistics.com/ko/tool/parcel/tracking")
                    .method(Connection.Method.GET)
                    .execute(); //첫번째 요청에 session 값가져옴
            Document document = responseget.parse();
            String csrf = document.select("input[name=_csrf]").attr("value"); //첫번째 요청에 csrf값 추출
            Document documentpost = Jsoup.connect("https://www.cjlogistics.com/ko/tool/parcel/tracking-detail")
                    .cookies(responseget.cookies())
                    .ignoreContentType(true) //json응답이므로 강제로 parsing 시도
                    .requestBody("_csrf="+csrf+"&paramInvcNo="+postNumber)
                    .post();

            JsonNode readTree = mapper.readTree(documentpost.body().text());

            JsonNode resultNode = readTree.findPath("parcelResultMap").findPath("resultList").get(0);
            resultMap.put("postNumber", resultNode.findPath("invcNo").asText());
            resultMap.put("sender", resultNode.findPath("sendrNm").asText());
            resultMap.put("receiver", resultNode.findPath("rcvrNm").asText());
            resultMap.put("contentType", resultNode.findPath("itemNm").asText());

            JsonNode detailNode = readTree.findPath("parcelDetailResultMap").findPath("resultList");
            JsonNode finNode = detailNode.get(detailNode.size()-1); //가장 최근 노드 추출
            resultMap.put("message", finNode.findPath("crgNm").asText());
            resultMap.put("statusData", finNode.findPath("dTime").asText());
            resultMap.put("status", finNode.findPath("scanNm").asText());
            resultMap.put("location", finNode.findPath("regBranNm").asText());

            PostDto postDto = PostDto.builder()
                    .postNumber(resultMap.get("postNumber"))
                    .sender(resultMap.get("sender"))
                    .receiver(resultMap.get("receiver"))
                    .contentType(resultMap.get("contentType"))
                    .statusData(resultMap.get("statusData"))
                    .status(resultMap.get("status"))
                    .message(resultMap.get("message"))
                    .location(resultMap.get("location"))
                    .build();
            return postDto;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (IndexOutOfBoundsException e){
            return null;
        } catch(RuntimeException e){
            return null;
        }
    }

    @Override
    public boolean isSupport(PostCompanyEnum postcompany) {
        if (postcompany == PostCompanyEnum.CJ){
            return true;
        }else{
            return false;
        }
    }
}

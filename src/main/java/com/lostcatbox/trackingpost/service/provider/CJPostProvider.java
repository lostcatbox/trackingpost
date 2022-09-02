package com.lostcatbox.trackingpost.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lostcatbox.trackingpost.domain.PostDto;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@Service
public class CJPostProvider implements PostProvider{
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

            JsonNode node = readTree.findPath("parcelResultMap").findPath("resultList").get(0);
            resultMap.put("postNumber", node.findPath("invcNo").asText());
            resultMap.put("sender", node.findPath("sendrNm").asText());
            resultMap.put("receiver", node.findPath("rcvrNm").asText());
            resultMap.put("contentType", node.findPath("itemNm").asText());

            JsonNode node2 = readTree.findPath("parcelDetailResultMap").findPath("resultList");
            JsonNode fin_node = node2.get(node2.size()-1); //가장 최근 노드 추출
            resultMap.put("message", fin_node.findPath("crgNm").asText());
            resultMap.put("statusData", fin_node.findPath("scanNm").asText());
            resultMap.put("location", fin_node.findPath("regBranNm").asText());

            PostDto postDto = PostDto.builder()
                    .postNumber(resultMap.get("postNumber"))
                    .sender(resultMap.get("sender"))
                    .receiver(resultMap.get("receiver"))
                    .contentType(resultMap.get("contentType"))
                    .statusData(resultMap.get("statusData"))
                    .message(resultMap.get("message"))
                    .location(resultMap.get("location"))
                    .build();
            return postDto;

        } catch (IOException e){
            e.printStackTrace();
            return null; //null처리?
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

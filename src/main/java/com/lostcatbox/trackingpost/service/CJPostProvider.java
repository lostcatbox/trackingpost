package com.lostcatbox.trackingpost.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
    public PostDto get(String post_number) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> resultMap = new HashMap<>();
        try {
            Connection.Response responseget = Jsoup.connect("https://www.cjlogistics.com/ko/tool/parcel/tracking")
                    .method(Connection.Method.GET)
                    .execute(); //첫번째 요청에 session 값가져옴
            Document document = Jsoup.connect("https://www.cjlogistics.com/ko/tool/parcel/tracking").cookies(responseget.cookies()).get(); //csrf값 추출위해 위에서 추출한 쿠키로 접근시도
            String csrf = document.select("input[name=_csrf]").attr("value");
            Document documentpost = Jsoup.connect("https://www.cjlogistics.com/ko/tool/parcel/tracking-detail")
                    .cookies(responseget.cookies())
                    .ignoreContentType(true) //강제로 parsing 시도 , 왜 documentType()이 null 일까?
//                    .header("accept","application/json, */*")
//                    .header("content-type", "application/x-www-form-urlencoded;")
//                    .header("accept-encoding", "gzip, deflate, br")
//                    .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .requestBody("_csrf="+csrf+"&paramInvcNo="+post_number)
                    .post();

            JsonNode readTree = mapper.readTree(documentpost.body().text());

            JsonNode node = readTree.findPath("parcelResultMap").findPath("resultList").get(0);
            resultMap.put("post_number", node.findPath("invcNo").asText());
            resultMap.put("sender", node.findPath("sendrNm").asText());
            resultMap.put("receiver", node.findPath("rcvrNm").asText());
            resultMap.put("content_type", node.findPath("itemNm").asText());

            JsonNode node2 = readTree.findPath("parcelDetailResultMap").findPath("resultList");
            JsonNode fin_node = node2.get(node2.size()-1);
            resultMap.put("message", fin_node.findPath("crgNm").asText());
            resultMap.put("status_data", fin_node.findPath("scanNm").asText());
            resultMap.put("location", fin_node.findPath("regBranNm").asText());

            PostDto postDto = PostDto.builder()
                    .post_number(resultMap.get("post_number"))
                    .sender(resultMap.get("sender"))
                    .receiver(resultMap.get("receiver"))
                    .content_type(resultMap.get("content_type"))
                    .status_data(resultMap.get("status_data"))
                    .message(resultMap.get("message"))
                    .location(resultMap.get("location"))
                    .build();
            return postDto;

        } catch (IOException e){
            e.printStackTrace();
            e.getMessage();
            e.getCause();
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

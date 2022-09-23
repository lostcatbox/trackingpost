package com.example.kakaochannel.service;

import com.example.trackingpostcore.domain.RequestInfo;
import com.example.trackingpostcore.domain.PostCompanyEnum;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ValidRequest { //추후에 데이터 받아서 정보 추출하는 역할 하는클래스
    public RequestInfo getinfo(String params){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new HashMap<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(params); //readTree는 @RequestBody에서 row 쓰면되는구나..

            resultMap.put("requestuser",jsonNode.findPath("userRequest").findPath("user").findPath("id").asText());

            JsonNode detailParams = jsonNode.findPath("detailParams");
            resultMap.put("post_company",detailParams.findPath("post_company").findPath("value").asText());
            resultMap.put("post_number",detailParams.findPath("post_number").findPath("value").asText());
            RequestInfo requestInfo = new RequestInfo();

            //log체크 ?? kakao 본섭에서는 왜 post_company안잡히지?
            log.info(jsonNode.asText());
            log.info(resultMap.toString());
            log.info(resultMap.get("post_company"));

            requestInfo.setPostCompany(PostCompanyEnum.valueOfName(resultMap.get("post_company")));

            requestInfo.setPostNumber(resultMap.get("post_number"));

            String requestuser = resultMap.get("requestuser");

            if (requestuser.length() <= 20) { //testServer시 requestuser length 20이하임
                requestInfo.setRequestUser(requestuser);
            } else {
                requestInfo.setRequestUser(requestuser.substring(0, 20)); //product기준은 requestuser 값 너무큼. 20자로 자름
            }
            return requestInfo;
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null; // "null에러시 반환"
        }
        catch (IOException e){
            e.printStackTrace();
            return null; // "에러시 빈것 반환"
        }
    }
}

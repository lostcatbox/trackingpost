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
    public RequestInfo getinfo(HttpServletRequest request){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new HashMap<>();
        try {
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            log.error(objectMapper.readTree(messageBody).asText());
            JsonNode jsonNode = objectMapper.readTree(messageBody).get(0);
            log.error(jsonNode.asText());
            resultMap.put("requestuser",jsonNode.findPath("userRequest").findPath("user").findPath("id").asText());

            JsonNode detailParams = jsonNode.findPath("detailParams");
            resultMap.put("post_company",detailParams.findPath("post_company").findPath("value").asText());
            resultMap.put("post_number",detailParams.findPath("post_number").findPath("value").asText());
            RequestInfo requestInfo = new RequestInfo();

            log.error(resultMap.get("post_company"));
            log.error(PostCompanyEnum.valueOf(resultMap.get("post_company")).getValue());
            requestInfo.setPostCompany(PostCompanyEnum.valueOf(resultMap.get("post_company")));
            requestInfo.setPostNumber(resultMap.get("post_number"));
            requestInfo.setRequestUser(resultMap.get("requestuser"));
            return requestInfo;
        }
        catch (IOException e){
            e.printStackTrace();
            return null; // "에러시 빈것 반환"
        }
    }
}

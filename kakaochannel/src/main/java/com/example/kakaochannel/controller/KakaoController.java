package com.example.kakaochannel.controller;

import com.example.kakaochannel.domain.KakaoLinkResponse;
import com.example.kakaochannel.service.ResponseService;
import com.example.trackingpostcore.aop.Logging;
import com.example.trackingpostcore.domain.RequestInfo;
import com.example.kakaochannel.service.ValidRequest;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import static javax.swing.UIManager.getString;

@RestController
@Slf4j
public class KakaoController {
    @Autowired
    ValidRequest validRequest;
    @Autowired
    ResponseService responseService;
    @Autowired
    private KafkaTemplate<String, RequestInfo> kafkaTemplate;

    private static final String topicName = "posttopic";

    @PostMapping(value = "/")
    @Logging
    public String gethomepage(@RequestBody String params) {
        RequestInfo requestInfo = validRequest.getinfo(params); // 요청에 대한 정보 추출
        String result = responseService.getFailResponse(); ////반드시 카카오엔 응답해야하는상황.서비스로빼고, advice에도 Exception후 return 으로 getFailResponse해도되는지
        if (requestInfo != null) { //null체크? catch하지못한오류? 혹시모르는오류?
            result = responseService.getSuccessResponse(requestInfo);
            kafkaTemplate.send(topicName, requestInfo);
        }
        return result;
    }
}


package com.example.kakaochannel.controller;

import com.example.trackingpostcore.domain.RequestInfo;
import com.example.kakaochannel.service.ValidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class KakaoController {
    @Autowired
    ValidRequest validRequest;
    @Autowired
    private KafkaTemplate<String, RequestInfo> kafkaTemplate;

    private static final String topicName = "posttopic";

    @GetMapping(value = "/")
    public String gethomepage(){
        RequestInfo requestInfo = validRequest.getinfo(); // 요청에 대한 정보 추출

        //kafka로 해당 요청 데이터 전송 to 외부 택배조회
        kafkaTemplate.send(topicName, requestInfo);


        return "localhost:8080"+"/"+requestInfo.getRequestUser()+"/"+requestInfo.getPostNumber()+"/";
    }
}

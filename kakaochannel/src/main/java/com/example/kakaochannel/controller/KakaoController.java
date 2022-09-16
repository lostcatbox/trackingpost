package com.example.kakaochannel.controller;

import com.example.kakaochannel.domain.KakaoLinkResponse;
import com.example.trackingpostcore.domain.RequestInfo;
import com.example.kakaochannel.service.ValidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KakaoController {
    @Autowired
    ValidRequest validRequest;
    @Autowired
    private KafkaTemplate<String, RequestInfo> kafkaTemplate;

    private static final String topicName = "posttopic";

//    @GetMapping(value = "/")
//    public String get(@RequestBody String params){
//        RequestInfo requestInfo = validRequest.getinfo(params); // 요청에 대한 정보 추출
////        if (requestInfo.equals(new RequestInfo())){ // 만약 요청에 해당하는 객체가 비어있을경우
//        if (requestInfo == null){
//            return "null에러";
//        }
//        //kafka로 해당 요청 데이터 전송 to 외부 택배조회
//        kafkaTemplate.send(topicName, requestInfo);
//
//        return "localhost:8080"+"/"+requestInfo.getRequestUser()+"/"+requestInfo.getPostNumber()+"/";
//    }
    @PostMapping(value = "/")
    public KakaoLinkResponse gethomepage(@RequestBody String params){
        RequestInfo requestInfo = validRequest.getinfo(params); // 요청에 대한 정보 추출
//        if (requestInfo.equals(new RequestInfo())){ // 만약 요청에 해당하는 객체가 비어있을경우
        if (requestInfo == null) { //네트워크 오류시 null처리 맞나 다시...확인 아닌거같은데?
            return new KakaoLinkResponse();
        } else if (requestInfo.getPostCompany() == null) { //지원하지않는 택배사 // 오류처리를 생성자로?? 맞냐?
            return new KakaoLinkResponse(requestInfo,"지원하지 않는 택배사입니다.");
        }
        //kafka로 해당 요청 데이터 전송 to 외부 택배조회
        else {
            kafkaTemplate.send(topicName, requestInfo);
            return new KakaoLinkResponse(requestInfo);
        }
    }
}

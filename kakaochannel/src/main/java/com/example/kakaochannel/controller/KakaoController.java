package com.example.kakaochannel.controller;

import com.example.kakaochannel.domain.KakaoLinkResponse;
import com.example.trackingpostcore.domain.RequestInfo;
import com.example.kakaochannel.service.ValidRequest;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Slf4j
public class KakaoController {
    @Autowired
    ValidRequest validRequest;
    @Autowired
    private KafkaTemplate<String, RequestInfo> kafkaTemplate;

    private static final String topicName = "posttopic";

    @PostMapping(value = "/")
    public String gethomepage(@RequestBody String params){
        Optional<RequestInfo> requestInfo = validRequest.getinfo(params); // 요청에 대한 정보 추출
        JsonObject obj = new JsonObject();
        obj.addProperty("version","2.0");
        JsonObject data = new JsonObject();

        requestInfo.ifPresentOrElse((r)-> {
            KakaoLinkResponse linkResponse = new KakaoLinkResponse(r); //해당 링크 작성된 DTO// 지역변수여야함.
            kafkaTemplate.send(topicName, r);
            data.addProperty("postDataLink", linkResponse.getPostDataLink());
            data.addProperty("requestUser", linkResponse.getRequestUser());
            data.addProperty("postCompany", linkResponse.getPostCompany().getName());
            data.addProperty("postNumber", linkResponse.getPostNumber());
        }, ()->{
            data.addProperty("postDataLink", "네트워크 오류 혹은 지원하지 않는 택배사");
                });

        obj.add("data", data);

        return obj.toString();
    }
}

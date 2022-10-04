package com.example.externalpost.controller;

import com.example.externalpost.service.PostDbService;
import com.example.externalpost.service.PostManager;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class PostController {
    @Autowired
    PostManager postManager;
    @Autowired
    PostDbService postDbService;

    private static final String topicName = "posttopic";


    //kafka로 해당 외부 택배조회 요청을 consumer 역할하는 것 추가
    //containerFactory 지정해주기
    @KafkaListener(topics = topicName, groupId="test", containerFactory = "kafkaListenerContainerFactory") //containerFactory중요
    public void kafkaconsumer(RequestInfo requestInfo){
        PostDto postDto = postManager.getpost(requestInfo);//해당 요청에 대해 post 정보 추출
        //응답 문제시 PostDto 객체이거나 null가능
        postDbService.savePost(postDto);
    }
    @KafkaListener(topics = topicName,groupId="logger", containerFactory = "kafkaListenerContainerFactory")
    public void kafkaloger(RequestInfo requestInfo){
        log.info("해당 요청 받은 시간: "+LocalDateTime.now().toString());
        log.info("해당 posttopic요청은 Consumer 처리완료: "+requestInfo.toString());
    }
}

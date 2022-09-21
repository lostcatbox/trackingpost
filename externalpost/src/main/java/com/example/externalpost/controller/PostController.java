package com.example.externalpost.controller;

import com.example.externalpost.service.PostDbService;
import com.example.externalpost.service.PostManager;
import com.example.trackingpostcore.domain.PostCompanyEnum;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

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
        Optional<PostDto> postDto = postManager.getpost(requestInfo);//해당 요청에 대해 post 정보 추출
        if (postDto.isPresent()) { //응답 문제시 PostDto 객체이거나 null가능
            postDbService.savePost(postDto.get());
            log.info("postDbService.savePost실행됨:" + postDto.toString());
        }
        else{
            log.error("saveDb Fail:postDto is null at "+postDto.toString());
        }

    }
    @KafkaListener(topics = topicName,groupId="logger", containerFactory = "kafkaListenerContainerFactory")
    public void kafkaloger(RequestInfo requestInfo){
        log.info(LocalDateTime.now().toString());
        log.info(requestInfo.toString());
    }
}

package com.example.externalpost.controller;

import com.example.externalpost.service.PostDbService;
import com.example.externalpost.service.PostManager;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
@Controller
public class PostController {
    @Autowired
    PostManager postManager;
    @Autowired
    PostDbService postDbService;

    private static final String topicName = "posttopic";


    //kafka로 해당 외부 택배조회 요청을 consumer 역할하는 것 추가
    @KafkaListener(topics = topicName, groupId="test", containerFactory = "kafkaListenerContainerFactory") //containerFactory중요
    public void kafkaconsumer(RequestInfo requestInfo){
        PostDto postDto = postManager.getpost(requestInfo); //해당 요청에 대해 post 정보 추출
        if (!postDto.getPostNumber().isEmpty()){ //응답 문제시 빈 PostDto객체이므로 not_found 리턴

            postDbService.savePost(postDto);
        }
    }
    @KafkaListener(topics = topicName,groupId="logger", containerFactory = "kafkaListenerContainerFactory")
    public void kafkaloger(RequestInfo requestInfo){
        System.out.println(LocalDateTime.now());
        System.out.println(requestInfo.toString());
    }



    //아래 로직은 새로고침 요청 시 들어가야할 로직
    //이미 모든 필드값같다면 modified_data만 바꿔주기
    @PostMapping(value = "/")
    public HttpStatus posthomepage(RequestInfo requestInfo){
        // 다른요청에 대한 추출 툴있어야함.
        PostDto postDto = postManager.getpost(requestInfo); //해당 요청에 대해 post 정보 추출
        if (postDto.getPostNumber().isEmpty()){ //응답 문제시 빈 PostDto객체이므로 not_found 리턴
            return HttpStatus.NOT_FOUND;
        }
        else{
            postDbService.savePost(postDto);
            return HttpStatus.OK;
        }
    }
}

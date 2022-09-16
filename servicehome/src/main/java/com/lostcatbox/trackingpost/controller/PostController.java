package com.lostcatbox.trackingpost.controller;

import com.example.trackingpostcore.domain.PostCompanyEnum;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.RequestInfo;
import com.lostcatbox.trackingpost.service.PostDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class PostController {
    @Autowired
    PostDbService postDbService;
    @Autowired
    KafkaTemplate<String, RequestInfo> kafkaTemplate;

    private static final String topicName = "posttopic";

    // 내 서버가 응답한 link경로로 접근한경우
    // id값은 DB아이디 값이며, kakaoid값으로 추후 바뀔수있다. 하지만 현재로는 이렇게 검증하고, 가장 최근 자료를 보여주는것이 좋은듯
    //카카오 임시로 일단 String 반환하게해놓음
    @GetMapping ("/{userId}/{postNumber}/")
    public String getpostpage(@PathVariable String userId,@PathVariable String postNumber){
        PostDto recentPost = postDbService.recentPost(userId, postNumber);//인자 &조건으로 가장최근 데이터 반환, 없다면 빈데이터
        recentPost.toString();
        return recentPost.toString();
    }

    //카카오 임시로 일단 String 반환하게해놓음
//    @GetMapping ("/{userId}/{postNumber}/")
//    public PostDto getpostpage(@PathVariable String userId,@PathVariable String postNumber){
//        PostDto recentPost = postDbService.recentPost(userId, postNumber);//인자 &조건으로 가장최근 데이터 반환, 없다면 빈데이터
//        return recentPost;
//    }
     //내 서버로 응답한 link경로로 접근한후 post로 새로고침 명령시 -> kafka로 외부 택배 조회 메세지 보내기 pub
    @PostMapping("/{userId}/{postNumber}/")
    public HttpStatus requestRefresh(@PathVariable String userId,@PathVariable String postNumber){
        RequestInfo requestInfo = new RequestInfo(postNumber,userId, PostCompanyEnum.CJ);
        kafkaTemplate.send(topicName,requestInfo);
        return HttpStatus.OK;
    }
}

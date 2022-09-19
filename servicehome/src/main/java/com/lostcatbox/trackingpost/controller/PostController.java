package com.lostcatbox.trackingpost.controller;

import com.example.trackingpostcore.domain.PostCompanyEnum;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.RequestInfo;
import com.lostcatbox.trackingpost.service.PostDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(origins="*", allowedHeaders = "*") //cors 관련 설정
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
    public PostDto getpost(@PathVariable String userId,@PathVariable String postNumber){
        PostDto recentPost = postDbService.getRecentPost(userId, postNumber);//인자 &조건으로 가장최근 데이터 반환, 없다면 null
        if (ObjectUtils.isEmpty(recentPost)){ //Object의 null값을 체크한다!
            return new PostDto();
        }
        else{
            return recentPost;
        }
    }
     //내 서버로 응답한 link경로로 접근한후 post로 새로고침 명령시 -> kafka로 외부 택배 조회 메세지 보내기 pub
    @PostMapping("/{userId}/{postNumber}/")
    public HttpStatus requestRefresh(@PathVariable String userId, @PathVariable String postNumber, @RequestParam String postCompany){
        PostCompanyEnum postCompanyEnum = PostCompanyEnum.valueOfName(postCompany);
        if (ObjectUtils.isEmpty(postCompanyEnum)){
            return HttpStatus.NOT_FOUND;
        }
        else{
            //바디체크후에 택배회사 가져오는 로직 필요함. -> 그리고 아래 로직에 해당 택배회사 Enum값으로 교체하는것필요 -> 실패시 404에러
            RequestInfo requestInfo = new RequestInfo(postNumber,userId, postCompanyEnum);
            kafkaTemplate.send(topicName,requestInfo);
            return HttpStatus.OK;
        }
    }
    //해당유저의 모든 택배조회 기록 반환
    @GetMapping("/{userId}/")
    public List<PostDto> getpostlist(@PathVariable String userId){
        List<PostDto> postList = postDbService.getPostList(userId);
        if (ObjectUtils.isEmpty(postList)){ //Object의 null값을 체크한다!
            return new ArrayList<>();
        }
        else{
            return postList;
        }
    }
}

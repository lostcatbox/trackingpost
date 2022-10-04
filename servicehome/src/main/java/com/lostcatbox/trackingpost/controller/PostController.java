package com.lostcatbox.trackingpost.controller;

import com.example.trackingpostcore.domain.PostCompanyEnum;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.RequestInfo;
import com.lostcatbox.trackingpost.advice.exception.NotFoundCompanyEnumCException;
import com.lostcatbox.trackingpost.domain.ResponsePost;
import com.lostcatbox.trackingpost.service.PostDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping ("/{userId}/{postNumber}/")
    public ResponsePost getpost(@PathVariable String userId,@PathVariable String postNumber){
        PostDto recentPost = postDbService.getRecentPost(userId, postNumber);//인자 &조건으로 가장최근 데이터 반환, 없다면 null
        return new ResponsePost(recentPost);
    }
     //내 서버로 응답한 link경로로 접근한후 post로 새로고침 명령시 -> kafka로 외부 택배 조회 메세지 보내기 pub
    @PostMapping("/{userId}/{postNumber}/")
    public HttpStatus requestRefresh(@PathVariable String userId, @PathVariable String postNumber, @RequestParam String postCompany){
        PostCompanyEnum postCompanyEnum = PostCompanyEnum.valueOfName(postCompany).orElseThrow(()-> new NotFoundCompanyEnumCException("servicehome 새로고침시 해당하는 택배사조회 못함"));
        RequestInfo requestInfo = new RequestInfo(postNumber,userId, postCompanyEnum);
        kafkaTemplate.send(topicName,requestInfo);
        return HttpStatus.OK;
    }
    //해당유저의 모든 택배조회 기록 반환
    @GetMapping("/{userId}/")
    public List<ResponsePost> getpostlist(@PathVariable String userId){
        List<PostDto> postList = postDbService.getPostList(userId);
        return convert(postList);
    }
    private List<ResponsePost> convert(List<PostDto> postList) {
        List<ResponsePost> responsePostList=new ArrayList<>();
        for (PostDto postdto: postList){
            ResponsePost responsePost = new ResponsePost(postdto);
            responsePostList.add(responsePost);
        }
        return responsePostList;
    }
}

package com.lostcatbox.trackingpost.controller;

import com.example.trackingpostcore.domain.PostDto;
import com.lostcatbox.trackingpost.service.PostDbService;
import com.lostcatbox.trackingpost.service.PostManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class PostController {
    @Autowired
    PostManager postManager;
    @Autowired
    PostDbService postDbService;

    // 내 서버가 응답한 link경로로 접근한경우
    // id값은 DB아이디 값이며, kakaoid값으로 추후 바뀔수있다. 하지만 현재로는 이렇게 검증하고, 가장 최근 자료를 보여주는것이 좋은듯
    @GetMapping ("/{userId}/{postNumber}/")
    public PostDto getpostpage(@PathVariable String userId,@PathVariable String postNumber){
        PostDto recentPost = postDbService.recentPost(userId, postNumber);//인자 &조건으로 가장최근 데이터 반환, 없다면 빈데이터
        return recentPost;
    }
    // 새로고침 만들기 post->인자 가져와서 이미 모든 필드값같다면 modified_data만 바꿔주기
//    @PostMapping (value = "/")
//    public HttpStatus posthomepage(){
//        // 다른요청에 대한 추출 툴있어야함.
//        PostDto postDto = postManager.getpost(requestInfo); //해당 요청에 대해 post 정보 추출
//        postDbService.savePost(postDto); //dbtest, 추후 다른 msa로 빠질것임 ->
//        return HttpStatus.OK;
//    }
}

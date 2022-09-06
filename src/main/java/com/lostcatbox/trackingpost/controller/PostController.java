package com.lostcatbox.trackingpost.controller;

import com.lostcatbox.trackingpost.domain.PostDto;
import com.lostcatbox.trackingpost.domain.RequestInfo;
import com.lostcatbox.trackingpost.service.KindRequest;
import com.lostcatbox.trackingpost.service.PostDbService;
import com.lostcatbox.trackingpost.service.PostManager;
import com.lostcatbox.trackingpost.service.ValidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class PostController {
    @Autowired
    PostManager postManager;
    @Autowired
    PostDbService postDbService;
    @Autowired
    ValidRequest validRequest;
    @GetMapping (value = "/")
    public PostDto gethomepage(){
        RequestInfo requestInfo = validRequest.getinfo(); // 요청에 대한 정보 추출
        PostDto postDto = postManager.getpost(requestInfo); //해당 요청에 대해 post 정보 추출
        postDbService.savePost(postDto); //dbtest, 추후 다른 msa로 빠질것임
        return postDto;
    }
}

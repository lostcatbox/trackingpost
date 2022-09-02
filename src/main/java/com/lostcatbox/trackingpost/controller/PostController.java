package com.lostcatbox.trackingpost.controller;

import com.lostcatbox.trackingpost.domain.PostDto;
import com.lostcatbox.trackingpost.service.PostDbService;
import com.lostcatbox.trackingpost.service.PostManager;
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
    @GetMapping (value = "/")
    public PostDto gethomepage(){
        PostDto postDto = postManager.getpost();
        postDbService.savePost(postDto); //dbtest, 추후 다른 msa로 빠질것임
        return postDto;
    }
}

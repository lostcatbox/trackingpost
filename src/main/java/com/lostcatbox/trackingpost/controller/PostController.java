package com.lostcatbox.trackingpost.controller;

import com.lostcatbox.trackingpost.domain.PostDto;
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
    @GetMapping (value = "/")
    public PostDto gethomepage(){
        return postManager.getpost();
    }
}

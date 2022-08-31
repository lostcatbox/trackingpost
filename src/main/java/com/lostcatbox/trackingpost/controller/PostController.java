package com.lostcatbox.trackingpost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class PostController {
    @GetMapping (value = "/")
    public String gethomepage(){
        return "hello_world";
    }
}

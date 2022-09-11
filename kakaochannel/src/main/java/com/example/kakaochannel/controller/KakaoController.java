package com.example.kakaochannel.controller;

import com.example.trackingpostcore.domain.RequestInfo;
import com.example.kakaochannel.service.ValidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class KakaoController {
    @Autowired
    ValidRequest validRequest;

    @GetMapping(value = "/")
    public String gethomepage(){
        RequestInfo requestInfo = validRequest.getinfo(); // 요청에 대한 정보 추출


        //kafka로 해당 요청 데이터 전송 to 외부 택배조회


        return "localhost:8080"+"/"+requestInfo.getRequestUser()+"/"+requestInfo.getPostNumber()+"/";
    }
}

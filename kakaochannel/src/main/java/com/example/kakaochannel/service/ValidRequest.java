package com.example.kakaochannel.service;

import com.example.kakaochannel.domain.RequestInfo;
import com.example.trackingpostcore.domain.KindRequest;
import com.example.trackingpostcore.domain.PostCompanyEnum;
import org.springframework.stereotype.Service;

@Service
public class ValidRequest { //추후에 데이터 받아서 정보 추출하는 역할 하는클래스
    public RequestInfo getinfo(){
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setPostCompany(PostCompanyEnum.CJ);
        requestInfo.setPostNumber("364321198184");
        requestInfo.setRequestUser("test2802");
        return requestInfo;
    }
}

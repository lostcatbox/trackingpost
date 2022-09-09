package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.RequestInfo;
import org.springframework.stereotype.Service;

@Service
public class ValidRequest { //추후에 데이터 받아서 정보 추출하는 역할 하는클래스
    public RequestInfo getinfo(){
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setKindRequest(KindRequest.Kakao);
        requestInfo.setPostCompany(PostCompanyEnum.CJ);
        requestInfo.setPostNumber("364321198184");
        requestInfo.setRequestUser("test2802");
        return requestInfo;
    }
}

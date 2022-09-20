package com.example.kakaochannel.domain;

import com.example.trackingpostcore.domain.PostCompanyEnum;
import com.example.trackingpostcore.domain.RequestInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoLinkResponse {
    private String postNumber;
    private String requestUser;
    private PostCompanyEnum postCompany;
    private String postDataLink;
    public KakaoLinkResponse(RequestInfo requestInfo){
        this.requestUser = requestInfo.getRequestUser();
        this.postCompany = requestInfo.getPostCompany();
        this.postNumber = requestInfo.getPostNumber();
        this.postDataLink ="http://trackingpost.lostcatbox.com:8080"+"/"+requestInfo.getRequestUser()+"/"+requestInfo.getPostNumber()+"/";;
    }
}

package com.example.trackingpostcore.domain;

import com.example.trackingpostcore.domain.KindRequest;
import com.example.trackingpostcore.domain.PostCompanyEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestInfo {
    private KindRequest kindRequest;
    private String postNumber;
    private String requestUser;
    private PostCompanyEnum postCompany;

    public RequestInfo(String postNumber, String requestUser, PostCompanyEnum postCompany) {
        this.postNumber = postNumber;
        this.requestUser = requestUser;
        this.postCompany = postCompany;
    }
}

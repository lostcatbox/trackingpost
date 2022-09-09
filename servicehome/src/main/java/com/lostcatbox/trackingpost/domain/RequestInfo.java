package com.lostcatbox.trackingpost.domain;

import com.lostcatbox.trackingpost.service.KindRequest;
import com.lostcatbox.trackingpost.service.PostCompanyEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestInfo {
    private KindRequest kindRequest;
    private String postNumber;
    private String requestUser;
    private PostCompanyEnum postCompany;
}

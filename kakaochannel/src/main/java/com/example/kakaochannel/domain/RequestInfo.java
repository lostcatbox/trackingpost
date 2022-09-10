package com.example.kakaochannel.domain;

import com.example.trackingpostcore.domain.KindRequest;
import com.example.trackingpostcore.domain.PostCompanyEnum;
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

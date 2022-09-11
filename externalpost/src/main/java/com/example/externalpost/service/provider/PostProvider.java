package com.example.externalpost.service.provider;

import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.PostCompanyEnum;

public interface PostProvider {

    PostDto get(String postNumber);

    boolean isSupport(PostCompanyEnum postcompany);
}

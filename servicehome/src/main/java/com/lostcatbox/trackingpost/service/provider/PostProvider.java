package com.lostcatbox.trackingpost.service.provider;

import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.PostCompanyEnum;

public interface PostProvider {

    PostDto get(String postNumber);

    boolean isSupport(PostCompanyEnum postcompany);
}

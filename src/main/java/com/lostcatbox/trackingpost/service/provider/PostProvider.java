package com.lostcatbox.trackingpost.service.provider;

import com.lostcatbox.trackingpost.domain.PostDto;
import com.lostcatbox.trackingpost.service.PostCompanyEnum;

public interface PostProvider {

    PostDto get(String postNumber);

    boolean isSupport(PostCompanyEnum postcompany);
}

package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.PostDto;

public interface PostProvider {

    PostDto get(String postNumber);

    boolean isSupport(PostCompanyEnum postcompany);
}

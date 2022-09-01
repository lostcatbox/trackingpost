package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.PostDto;

public interface PostProvider {

    PostDto get(String post_number);

    boolean isSupport(PostCompanyEnum postcompany);
}

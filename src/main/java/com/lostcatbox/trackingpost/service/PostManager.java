package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostManager {
    @Autowired
    CUPostProvider cuPostProvider;
    @Autowired
    CJPostProvider cjPostProvider;

    public PostDto getpost(){
        Map<String, Object> info = getinfo();
        if (cuPostProvider.isSupport((PostCompanyEnum) info.get("post_company"))){
            PostDto result = cuPostProvider.get((String) info.get("post_number"));
            return result;
        }else{
            PostDto result = cjPostProvider.get((String) info.get("post_number"));
            return result;
        }
    }
    public Map<String,Object> getinfo(){ //test, 추후 요청에 대해 분석해주는 Service 만들거나, controller get인자로 받을에정
        HashMap<String , Object> postInfoMap = new HashMap<>();
        postInfoMap.put("post_company", PostCompanyEnum.CJ);
        postInfoMap.put("post_number", "364321198184");
        return postInfoMap;
    }
}

package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.PostDto;
import com.lostcatbox.trackingpost.service.provider.CJPostProvider;
import com.lostcatbox.trackingpost.service.provider.CUPostProvider;
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
        if (cuPostProvider.isSupport((PostCompanyEnum) info.get("postCompany"))){
            PostDto result = cuPostProvider.get((String) info.get("postNumber"));
            result.setKakaoId((String) info.get("kakaoId"));
            return result;
        }else if(cjPostProvider.isSupport((PostCompanyEnum) info.get("postCompany"))){
            PostDto result = cjPostProvider.get((String) info.get("postNumber"));
            result.setKakaoId((String) info.get("kakaoId"));
            return result;
        }else{
            return null; //null 임시 처리
        }
    }
    public Map<String,Object> getinfo(){ //test, 추후 요청에 대해 분석해주는 Service 만들거나, controller get인자로 받을에정
        HashMap<String , Object> postInfoMap = new HashMap<>();
        postInfoMap.put("postCompany", PostCompanyEnum.CU);
        postInfoMap.put("postNumber", "364321267646");
        postInfoMap.put("kakaoId", "test2802");
        return postInfoMap;
    }
}

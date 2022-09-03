package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.PostDto;
import com.lostcatbox.trackingpost.service.provider.CJPostProvider;
import com.lostcatbox.trackingpost.service.provider.CUPostProvider;
import com.lostcatbox.trackingpost.service.provider.PostProvider;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostManager {

    private final List<PostProvider> providerList;
    @Autowired
    public PostManager(List<PostProvider> providerList) { // bean에서 해당 타입으로 등록된 빈 list로 주입
        this.providerList = providerList;
    }

    public PostDto getpost(){
        Map<String, Object> info = getinfo();
        for (PostProvider provider:providerList){
            if (!provider.isSupport((PostCompanyEnum) info.get("postCompany"))) { //support확인후 아니면패스
                continue;
            }
            else{
                PostDto result = provider.get((String) info.get("postNumber"));
                if (result!=null){ //result 네트워크 오류시 null반환함
                    result.setKakaoId((String) info.get("kakaoId"));
                    return result;
                }
                else{
                    return new PostDto(); //null대신 에러를 대체할만한 객체필요
                }
            }
        } //for 문을 다돌아도없다?
        return new PostDto(); //null대신 에러를 대체할만한 객체필요
    }
    public Map<String,Object> getinfo(){ //test, 추후 요청에 대해 분석해주는 Service 만들거나, controller get인자로 받을에정
        HashMap<String , Object> postInfoMap = new HashMap<>();
        postInfoMap.put("postCompany", PostCompanyEnum.CJ);
        postInfoMap.put("postNumber", "364321198184");
        postInfoMap.put("kakaoId", "test2802");
        return postInfoMap;
    }
}

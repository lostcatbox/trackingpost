package com.example.externalpost.service;

import com.example.externalpost.service.provider.PostProvider;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostManager {

    private final List<PostProvider> providerList;
    @Autowired
    public PostManager(List<PostProvider> providerList) { // bean에서 해당 타입으로 등록된 빈 list로 주입
        this.providerList = providerList;
    }

    public Optional<PostDto> getpost(RequestInfo requestInfo){
        for (PostProvider provider:providerList){
                if (!provider.isSupport(requestInfo.getPostCompany())) { //support확인후 아니면패스
                continue;
            }
            else{
                PostDto result = provider.get(requestInfo.getPostNumber());
                if (result!=null){ //result 네트워크 오류시 null반환함
                    result.setKakaoId(requestInfo.getRequestUser());
                    result.setPostCompany(requestInfo.getPostCompany());
                    return Optional.ofNullable(result);
                }
                else{
                    return Optional.ofNullable(null); //null대신 에러를 대체할만한 객체필요
                }
            }
        } //for 문을 다돌아도없다?
        return Optional.ofNullable(null); //null대신 에러를 대체할만한 객체필요
    }
}

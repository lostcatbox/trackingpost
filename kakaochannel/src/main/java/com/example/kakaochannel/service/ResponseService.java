package com.example.kakaochannel.service;

import com.example.kakaochannel.domain.KakaoLinkResponse;
import com.example.trackingpostcore.domain.RequestInfo;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public String getSuccessResponse(RequestInfo requestInfo) {
        JsonObject obj = new JsonObject();
        obj.addProperty("version","2.0");
        JsonObject data = new JsonObject();

        KakaoLinkResponse linkResponse = new KakaoLinkResponse(requestInfo); //해당 링크 작성된 DTO// 지역변수여야함.
        data.addProperty("postDataLink", linkResponse.getPostDataLink());
        data.addProperty("requestUser", linkResponse.getRequestUser());
        data.addProperty("postCompany", linkResponse.getPostCompany().getName());
        data.addProperty("postNumber", linkResponse.getPostNumber());

        obj.add("data", data);

        return obj.toString();
    }
    public String getFailResponse() {
        JsonObject obj = new JsonObject();
        obj.addProperty("version","2.0");
        JsonObject data = new JsonObject();

        data.addProperty("postDataLink", "네트워크 오류 혹은 지원하지 않는 택배사");
        obj.add("data", data);
        return obj.toString();
    }
}

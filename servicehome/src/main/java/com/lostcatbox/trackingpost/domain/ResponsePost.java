package com.lostcatbox.trackingpost.domain;

import com.example.trackingpostcore.domain.Post;
import com.example.trackingpostcore.domain.PostCompanyEnum;
import com.example.trackingpostcore.domain.PostDto;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponsePost {
    private String kakaoId; //kakao id
    private String postCompany; //택배회사
    private String postNumber; //송장번호
    private String sender; //보내는이
    private String receiver; //받는이
    private String contentType; //내용물 타입
    private String message; // detail한 메세지
    private String location; // 위치(허브->허브 or담당지)
    private String statusData; //택배사의 기록 업데이트 날짜
    private String status; // 간선상차, 하차 등등
    private LocalDateTime createdDate; // 처음 만들어진 날짜
    private LocalDateTime modifiedDate; //최근 정보 업데이트 날짜

    public ResponsePost(PostDto postDto) { //post모든 정보 get
        this.kakaoId = postDto.getKakaoId();
        this.postCompany = postDto.getPostCompany().getName();
        this.postNumber = postDto.getPostNumber();
        this.sender = postDto.getSender();
        this.receiver = postDto.getReceiver();
        this.contentType = postDto.getContentType();
        this.message = postDto.getMessage();
        this.location = postDto.getLocation();
        this.statusData = postDto.getStatusData();
        this.status = postDto.getStatus();
        this.createdDate = postDto.getCreatedDate();
        this.modifiedDate = postDto.getModifiedDate();
    }
}

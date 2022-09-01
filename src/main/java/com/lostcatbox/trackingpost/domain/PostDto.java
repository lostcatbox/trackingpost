package com.lostcatbox.trackingpost.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    String post_number; //송장번호
    String sender; //보내는이
    String receiver; //받는이
    String content_type; //내용물 타입
    String message; // detail한 메세지
    String location; // 위치(허브->허브 or담당지)
    String status_data; //택배사의 기록 업데이트 날짜

    @Builder
    public PostDto(String post_number, String sender, String receiver, String content_type, String message, String location, String status_data) {
        this.post_number = post_number;
        this.sender = sender;
        this.receiver = receiver;
        this.content_type = content_type;
        this.message = message;
        this.location = location;
        this.status_data = status_data;
    }
}

package com.example.trackingpostcore.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Long id; //unique id
    private String kakaoId; //kakao id
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

    public Post toEntity(){
        Post post = Post.builder()
                .id(id)
                .kakaoId(kakaoId)
                .postNumber(postNumber)
                .sender(sender)
                .receiver(receiver)
                .contentType(contentType)
                .message(message)
                .location(location)
                .statusData(statusData)
                .status(status)
                .build();
        return post;
    }
    @Builder     //id 까지 builder에 포함해야하나? 근데 수정할때도 post로 가져와서 update()함수 만들어놓으면 필요없긴한데?
    public PostDto(Long id, String kakaoId, String postNumber, String sender, String receiver, String contentType, String message, String location, String statusData, String status) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.postNumber = postNumber;
        this.sender = sender;
        this.receiver = receiver;
        this.contentType = contentType;
        this.message = message;
        this.location = location;
        this.statusData = statusData;
        this.status = status;
    }

    public PostDto(Post post) { //post모든 정보 get
        this.id = post.getId();
        this.kakaoId = post.getKakaoId();
        this.postNumber = post.getPostNumber();
        this.sender = post.getSender();
        this.receiver = post.getReceiver();
        this.contentType = post.getContentType();
        this.message = post.getMessage();
        this.location = post.getLocation();
        this.statusData = post.getStatusData();
        this.status = post.getStatus();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }
}

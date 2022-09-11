package com.example.trackingpostcore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue
    private Long id; //unique id
    @Column(length=50,nullable =false)
    private String kakaoId; //kakao id
    @Column(length=50,nullable =false)
    private String postNumber; //송장번호
    @Column(length=20,nullable =false)
    private String sender; //보내는이
    @Column(length=20,nullable =false)
    private String receiver; //받는이
    @Column(length=100)
    private String contentType; //내용물 타입
    @Column(columnDefinition="TEXT")
    private String message; // detail한 메세지
    @Column(length=100,nullable =false)
    private String location; // 위치(허브->허브 or담당지)
    @Column(length=100,nullable =false)
    private String statusData; //택배사의 기록 업데이트 날짜
    @Column(length=100,nullable =true)
    private String status; //택배 생태 기록

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate; // 처음 만들어진 날짜

    @LastModifiedDate
    private LocalDateTime modifiedDate; //최근 정보 업데이트 날짜

    @Builder
    public Post(Long id, String kakaoId, String postNumber, String sender, String receiver, String contentType, String message, String location, String statusData, String status) {
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
    public void update(LocalDateTime modifiedDate){
        this.modifiedDate = modifiedDate;
    }
}

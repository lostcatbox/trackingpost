package com.lostcatbox.trackingpost.service;

import com.example.trackingpostcore.domain.Post;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostDbService {
    private final PostRepository postRepository;

    //송장번호중 가장 최근 post정보 가져옴
    public PostDto recentPost(String kakaoId, String postNumber){
        Optional<Post> recentPost = postRepository.findTopByPostNumberAndKakaoIdOrderByModifiedDateDesc(postNumber,kakaoId);
        if (ObjectUtils.isEmpty(recentPost)){
            return new PostDto(); //조회경력없음. 빈 post
        }
        else{
            return new PostDto(recentPost.get()); //최근데이터 하나만 반환함
        }
    }
}

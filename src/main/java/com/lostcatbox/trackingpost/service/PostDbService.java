package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.Post;
import com.lostcatbox.trackingpost.domain.PostDto;
import com.lostcatbox.trackingpost.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostDbService {
    private final PostRepository postRepository;

    //save
    public PostDto savePost(PostDto postDto){
        postRepository.save(postDto.toEntity());
        return postDto;
    }
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

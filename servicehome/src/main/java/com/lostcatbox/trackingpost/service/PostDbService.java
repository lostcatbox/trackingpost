package com.lostcatbox.trackingpost.service;

import com.example.trackingpostcore.domain.Post;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.repository.PostRepository;
import com.lostcatbox.trackingpost.advice.exception.NotFoundPostCException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostDbService {
    private final PostRepository postRepository;

    //송장번호중 가장 최근 post정보 가져옴
    public PostDto getRecentPost(String kakaoId, String postNumber){
        Post recentPost = postRepository.findTopByPostNumberAndKakaoIdOrderByModifiedDateDesc(postNumber,kakaoId).orElseThrow(
                ()-> new NotFoundPostCException("RecentPost호출시 해당정보로된 Post 없음"));
        return new PostDto(recentPost);
    }
    public List<PostDto> getPostList(String kakaoId){
        List<Post> postList = postRepository.findAllByKakaoIdOrderByModifiedDateDesc(kakaoId);
        List<PostDto> postDtoList = new ArrayList<>();

        if (ObjectUtils.isEmpty(postList)){
            throw new NotFoundPostCException("PostList 호출시 해당정보로된 Post 없음");
        }

        for(Post post : postList) {
            PostDto postDto = new PostDto(post);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }
}

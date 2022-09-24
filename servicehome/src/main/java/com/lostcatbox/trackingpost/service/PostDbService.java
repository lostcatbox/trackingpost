package com.lostcatbox.trackingpost.service;

import com.example.trackingpostcore.domain.Post;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.repository.PostRepository;
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
    public Optional<PostDto> getRecentPost(String kakaoId, String postNumber){
        Optional<Post> recentPost = postRepository.findTopByPostNumberAndKakaoIdOrderByModifiedDateDesc(postNumber,kakaoId);
        return Optional.of(new PostDto(recentPost.orElseGet(null))); //객체있다면 PostDto 옵셔널로 반환 아니면 null로 옵셔널 반환
    }
    public List<PostDto> getPostList(String kakaoId){
        List<Post> postList = postRepository.findAllByKakaoIdOrderByModifiedDateDesc(kakaoId);
        List<PostDto> postDtoList = new ArrayList<>(); //비었다면?
        if (ObjectUtils.isEmpty(postList)){ //list도 optional처럼 바꿀수있지않을까?-->?
            return Collections.emptyList();
        }
        else{
            for(Post post : postList) {
                PostDto postDto = new PostDto(post);
                postDtoList.add(postDto);
            }
            return postDtoList;
        }
    }
}

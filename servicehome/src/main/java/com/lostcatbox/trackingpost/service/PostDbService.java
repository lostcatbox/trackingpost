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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostDbService {
    private final PostRepository postRepository;

    //송장번호중 가장 최근 post정보 가져옴
    public PostDto getRecentPost(String kakaoId, String postNumber){
        Optional<Post> recentPost = postRepository.findTopByPostNumberAndKakaoIdOrderByModifiedDateDesc(postNumber,kakaoId);
        if (ObjectUtils.isEmpty(recentPost)){
            return null;
        }
        else{
            return new PostDto(recentPost.get()); //최근데이터 하나만 반환함
        }
    }
    public List<PostDto> getPostList(String kakaoId){
        List<Post> postList = postRepository.findAllByKakaoIdOrderByModifiedDateDesc(kakaoId);
        List<PostDto> postDtoList = new ArrayList<>();
        if (postList.isEmpty()){
            return null;
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

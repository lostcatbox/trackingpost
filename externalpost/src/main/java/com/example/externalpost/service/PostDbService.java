package com.example.externalpost.service;

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

    //save
    @Transactional
    public PostDto savePost(PostDto postDto) {
        Optional<Post> recentPost = postRepository.findTopByPostNumberAndKakaoIdOrderByModifiedDateDesc(postDto.getPostNumber(), postDto.getKakaoId());
        if (ObjectUtils.isEmpty(recentPost)) {
            postRepository.save(postDto.toEntity());
        } else {
            if (recentPost.get().getStatusData().equals(postDto.getStatusData())) {
                recentPost.get().update(LocalDateTime.now()); //spring에서 한 트랜젝션에서의 변경은 더티체킹일어남
            } else {
                postRepository.save(postDto.toEntity());
            }
        }

        return postDto;
    }
}

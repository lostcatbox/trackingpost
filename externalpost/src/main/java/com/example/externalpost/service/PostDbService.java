package com.example.externalpost.service;

import com.example.trackingpostcore.domain.Post;
import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostDbService {
    private final PostRepository postRepository;
    @Transactional
    public PostDto savePost(PostDto postDto) {
        //Optional 반환하는 JPA사용시 Optional를 바로 null 체크하여, 값을 무조건 갖도록한다.(NPE피함+Optional로 책임전가 불가)
        //추후 JPA에서 boolen사용하자
        Post recentPost = postRepository.findTopByPostNumberAndKakaoIdOrderByModifiedDateDesc(postDto.getPostNumber(), postDto.getKakaoId()).orElse(Post.getDefaultErrorPost());
        if (recentPost.getStatusData().equals(postDto.getStatusData())) {
            recentPost.update(LocalDateTime.now()); //spring에서 한 트랜젝션에서의 변경은 더티체킹일어남
            log.info("해당 택배 상태는 이미 최신 것: "+postDto);
            return postDto;
        }
        postRepository.save(postDto.toEntity());
        log.info("해당 택배를 새로이 업데이트함: "+postDto);
        return postDto;
    }
}
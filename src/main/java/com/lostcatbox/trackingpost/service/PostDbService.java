package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.PostDto;
import com.lostcatbox.trackingpost.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDbService {
    private final PostRepository postRepository;

    //save
    public PostDto savePost(PostDto postDto){
        postRepository.save(postDto.toEntity());
        return postDto;
    }
}

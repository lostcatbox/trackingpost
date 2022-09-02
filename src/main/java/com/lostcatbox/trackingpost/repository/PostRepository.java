package com.lostcatbox.trackingpost.repository;

import com.lostcatbox.trackingpost.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long>{
        Optional<Post> findById(Long Id);
        Optional<Post> findAllByKakaoId(String kakaoId);
}

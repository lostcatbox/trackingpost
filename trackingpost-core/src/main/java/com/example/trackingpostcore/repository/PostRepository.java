package com.example.trackingpostcore.repository;

import com.example.trackingpostcore.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long>{
        Optional<Post> findById(Long id);

        Optional<Post> findTopByPostNumberAndKakaoIdOrderByModifiedDateDesc(String postNumber,String kakaoId);
        List<Post> findAllByKakaoIdOrderByModifiedDateDesc(String kakaoId);
        boolean existsPostByPostNumberAndKakaoId(String postNumber, String kakaoId);

}

package com.hah.social.repository;

import com.hah.social.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.user.id=:userId")
    List<Post> findPostByUserId(Long userId);
}

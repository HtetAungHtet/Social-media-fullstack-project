package com.hah.social.service;

import com.hah.social.model.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> findAllPosts();

    Post findPostById(Long postId);

    List<Post> findPostByUserId(Long userId);

    Post createPost(Post post, Long userId);

    Post savePost(Long postId, Long userId);

    Post likePost(Long postId, Long userId);

    String deletePost(Long postId, Long userId);
}

package com.hah.social.service;

import com.hah.social.model.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> findAllPosts();

    Post findPostById(Long postId) throws Exception;

    List<Post> findPostByUserId(Long userId);

    Post createNewPost(Post post, Long userId) throws Exception;

    Post savePost(Long postId, Long userId) throws Exception;

    Post likePost(Long postId, Long userId) throws Exception;

    String deletePost(Long postId, Long userId) throws Exception;
}

package com.hah.social.service;

import com.hah.social.model.entity.Comment;

public interface CommentService {

        Comment createComment(Comment comment,Long userId, Long postId) throws Exception;

        Comment findCommentById(Long commentId) throws Exception;

        Comment likeComment(Long commentId, Long userId) throws Exception;

        String deleteComment(Long commentId, Long userId) throws Exception;
}

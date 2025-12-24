package com.hah.social.service.impl;

import com.hah.social.error.UserNotFoundException;
import com.hah.social.model.entity.Comment;
import com.hah.social.model.entity.Post;
import com.hah.social.model.entity.User;
import com.hah.social.repository.CommentRepository;
import com.hah.social.repository.PostRepository;
import com.hah.social.service.CommentService;
import com.hah.social.service.PostService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Long userId, Long postId) throws Exception {
        Optional<User> opt = userService.findUserById(userId);
        if (opt.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        User user = opt.get();

        Post post = postService.findPostById(postId);
        
        comment.setUser(user);
        comment.setContent(comment.getContent());

        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);

        postRepository.save(post);
        return savedComment;
    }

    @Override
    public Comment findCommentById(Long commentId) throws Exception {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);

        if (commentOpt.isEmpty()) {
            throw new Exception("comment not exist");
        }
        return commentOpt.get();
    }

    @Override
    public Comment likeComment(Long commentId, Long userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId)
                .orElseThrow(()->new UserNotFoundException(userId));

        if (comment.getLiked().contains(user))
        {
            comment.getLiked().remove(user);
        }
        else
        {
            comment.getLiked().add(user);
        }
        return commentRepository.save(comment);
    }

    @Override
    public String deleteComment(Long commentId, Long userId) throws Exception {

        Comment comment = commentRepository.findById(commentId).orElseThrow();

        System.out.println("Comment user ID: " + comment.getUser().getId());
        System.out.println("Request user ID: " + userId);
        if (!comment.getUser().getId().equals(userId))
        {
            throw new Exception("Not comment owner!");
        }

        commentRepository.delete(comment);
        return "Comment deleted successfully";
    }

}

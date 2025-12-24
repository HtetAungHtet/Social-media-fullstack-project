package com.hah.social.controller;

import com.hah.social.model.entity.Comment;
import com.hah.social.model.entity.User;
import com.hah.social.response.ApiResponse;
import com.hah.social.service.CommentService;
import com.hah.social.service.PostService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final UserService userService;
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/post/{postId}")
    public Comment createComment(@RequestBody Comment comment,
                                 @RequestHeader("Authorization") String jwt,
                                 @PathVariable Long postId) throws Exception {

        User reqUser = userService.findUserByToken(jwt);
        return commentService.createComment(comment, reqUser.getId(), postId);
    }

    @PutMapping("/like/{commentId}")
    public Comment likeComment(@RequestHeader("Authorization") String jwt,
                               @PathVariable Long commentId) throws Exception {

        User reqUser = userService.findUserByToken(jwt);
        return commentService.likeComment(commentId, reqUser.getId());
    }

    @GetMapping("/{commentId}")
    public Comment findCommentById(@PathVariable Long commentId) throws Exception {
        return commentService.findCommentById(commentId);
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteCommentHandler(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long commentId,
                                                         @PathVariable Long postId) throws Exception {
        User reqUser = userService.findUserByToken(jwt);
        String message = commentService.deleteComment(commentId, reqUser.getId());
        ApiResponse response = new ApiResponse(message, true);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}

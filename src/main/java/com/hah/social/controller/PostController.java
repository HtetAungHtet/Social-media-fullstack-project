package com.hah.social.controller;

import com.hah.social.error.ApiResponse;
import com.hah.social.model.entity.Post;
import com.hah.social.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> findAllPosts() {
        List<Post> post = postService.findAllPosts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Post> createNewPost(@RequestBody Post post,
                                              @PathVariable Long userId) throws Exception {
        Post createdPost = postService.createNewPost(post, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPost);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Long postId) throws Exception {
        Post post = postService.findPostById(postId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> findPostByUserId(@PathVariable Long userId){
        List<Post> post = postService.findPostByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    @DeleteMapping("/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse> deletePostHandler(@PathVariable Long postId,
                                                  @PathVariable Long userId) throws Exception {
        String message = postService.deletePost(postId, userId);
        ApiResponse response = new ApiResponse(message, true);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/save/{postId}/user/{userId}")
    public ResponseEntity<Post> savePostHandler(@PathVariable Long postId,
                                          @PathVariable Long userId) throws Exception {
        Post post = postService.savePost(postId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    @PutMapping("/like/{postId}/user/{userId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Long userId,
                                          @PathVariable Long postId) throws Exception {
        Post post = postService.likePost(postId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }
}

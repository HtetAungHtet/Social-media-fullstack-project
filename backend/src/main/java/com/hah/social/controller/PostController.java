package com.hah.social.controller;

import com.hah.social.model.entity.User;
import com.hah.social.response.ApiResponse;
import com.hah.social.model.entity.Post;
import com.hah.social.service.PostService;
import com.hah.social.service.UserService;
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

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Post>> findAllPosts() {
        List<Post> post = postService.findAllPosts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    @PostMapping
    public ResponseEntity<Post> createNewPost(@RequestBody Post post,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByToken(jwt);
        Post createdPost = postService.createNewPost(post, reqUser.getId());
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

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePostHandler(@PathVariable Long postId,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByToken(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse response = new ApiResponse(message, true);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/save/{postId}")
    public ResponseEntity<Post> savePostHandler(@PathVariable Long postId,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByToken(jwt);
        Post post = postService.savePost(postId, reqUser.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Long postId,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByToken(jwt);
        Post post = postService.likePost(postId, reqUser.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(post);
    }
}

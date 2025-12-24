package com.hah.social.service.impl;

import com.hah.social.model.entity.Post;
import com.hah.social.model.entity.User;
import com.hah.social.repository.PostRepository;
import com.hah.social.repository.UserRepository;
import com.hah.social.service.PostService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public List<Post> findAllPosts() {

        return postRepository.findAll();
    }

    @Override
    public Post findPostById(Long postId) throws Exception {

        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new Exception("Post not found with id : " + postId);
        }
        return post.get();
    }

    @Override
    public List<Post> findPostByUserId(Long userId) {

        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post createNewPost(Post post, Long userId) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found!"));

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setUser(user);
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post savePost(Long postId, Long userId) throws Exception {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Exception("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
        } else {
            user.getSavedPost().add(post);
        }

        userRepository.save(user);   // user is managed; relation table will be updated
        return post;
    }


    @Override
    public Post likePost(Long postId, Long userId) throws Exception {
        Post post = findPostById(postId);
        Optional<User> userOpt = userService.findUserById(userId);

        if (userOpt.isEmpty()) {
            throw new Exception("User not found!");
        }

        User user = userOpt.get();

        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        }
        else {
           post.getLiked().add(user);
        }
        return postRepository.save(post);
    }

    @Override
    public String deletePost(Long postId, Long userId) throws Exception {
        Post post = findPostById(postId);
        Optional<User> userOpt = userService.findUserById(userId);

        if (userOpt.isEmpty()) {
            throw new Exception("User not found!");
        }

        User user = userOpt.get();

        if (!post.getUser().getId().equals(user.getId())) {
            throw new Exception("You can't delete another user's post!!!");
        }

        postRepository.delete(post);
        return "Post deleted successfully";
    }
}

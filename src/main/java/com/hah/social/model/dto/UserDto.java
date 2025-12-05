package com.hah.social.model.dto;

import com.hah.social.model.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<Long> followers = new ArrayList<>();

    private List<Long> followings = new ArrayList<>();

    private String gender;

    private List<Post> posts = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

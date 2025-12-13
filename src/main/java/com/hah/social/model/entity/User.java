package com.hah.social.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class User extends BaseEntity{

    private String firstName;

    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @JsonIgnore
    private List<Long> followers = new ArrayList<>();

    @JsonIgnore
    private List<Long> followings = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<Post> savedPost = new ArrayList<>();

    private String gender;
}

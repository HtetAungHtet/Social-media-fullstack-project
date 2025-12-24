package com.hah.social.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Post extends BaseEntity{

    private String caption;

    private String image;

    private String video;

    //@JsonIgnore
    @OneToMany
    private List<User> liked = new ArrayList<>();

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

}

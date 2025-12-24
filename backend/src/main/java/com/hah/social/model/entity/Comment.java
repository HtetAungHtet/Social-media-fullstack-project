package com.hah.social.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Comment extends BaseEntity{

    private String content;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<User> liked = new ArrayList<>();
}

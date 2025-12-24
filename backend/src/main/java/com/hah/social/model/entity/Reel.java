package com.hah.social.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Reel extends BaseEntity{

    private String title;

    private String video;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

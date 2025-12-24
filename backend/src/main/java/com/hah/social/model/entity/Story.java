package com.hah.social.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Story extends BaseEntity{

    private String captions;

    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

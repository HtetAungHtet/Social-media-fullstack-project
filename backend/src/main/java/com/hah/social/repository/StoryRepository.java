package com.hah.social.repository;

import com.hah.social.model.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findByUserId(Long userId);
}

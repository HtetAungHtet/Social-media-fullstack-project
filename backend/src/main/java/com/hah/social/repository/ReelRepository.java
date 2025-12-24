package com.hah.social.repository;

import com.hah.social.model.entity.Reel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Long> {

    List<Reel> findByUserId(Long userId);
}

package com.hah.social.service;

import com.hah.social.model.entity.Reel;
import com.hah.social.model.entity.User;

import java.util.List;

public interface ReelService {

    List<Reel> findAllReels();

    Reel createReel(Reel reel, User user);

    List<Reel> findUserReels(Long userId);
}

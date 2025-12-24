package com.hah.social.service.impl;

import com.hah.social.model.entity.Reel;
import com.hah.social.model.entity.User;
import com.hah.social.repository.ReelRepository;
import com.hah.social.service.ReelService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReelServiceImpl implements ReelService {

    private final ReelRepository reelRepository;
    private final UserService userService;

    @Override
    public List<Reel> findAllReels() {
        return reelRepository.findAll();
    }

    @Override
    public Reel createReel(Reel reel, User user) {
        Reel createdReel = new Reel();
        createdReel.setTitle(reel.getTitle());
        createdReel.setUser(user);
        createdReel.setVideo(reel.getVideo());
        return reelRepository.save(createdReel);
    }

    @Override
    public List<Reel> findUserReels(Long userId) {
        userService.findUserById(userId);
        return reelRepository.findByUserId(userId);
    }
}

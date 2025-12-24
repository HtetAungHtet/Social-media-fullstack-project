package com.hah.social.controller;

import com.hah.social.model.entity.Reel;
import com.hah.social.model.entity.User;
import com.hah.social.service.ReelService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reels")
public class ReelController {

    private final ReelService reelService;
    private final UserService userService;

    @PostMapping
    public Reel createReel(@RequestBody Reel reel,
                           @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserByToken(jwt);
        return reelService.createReel(reel, reqUser);
    }

    @GetMapping
    public List<Reel> findAllReels() {
        return reelService.findAllReels();
    }

    @GetMapping("/user/{userId}")
    public List<Reel> findUserReel(@PathVariable Long userId) {
        System.out.println(" user id : " + userId);
        return reelService.findUserReels(userId);
    }
}

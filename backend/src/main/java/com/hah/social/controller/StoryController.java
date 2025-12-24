package com.hah.social.controller;

import com.hah.social.model.entity.Story;
import com.hah.social.model.entity.User;
import com.hah.social.service.StoryService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/story")
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;

    @PostMapping
    public Story createStory(@RequestBody Story story,
                             @RequestHeader("Authorization") String jwt) {
        User reqUser = userService.findUserByToken(jwt);
        return storyService.createStory(story, reqUser);
    }

    @GetMapping("/user/{userId}")
    public List<Story> findStoryByUserId(@PathVariable Long userId) {
        return storyService.findStoryByUserId(userId);
    }
}

package com.hah.social.service.impl;

import com.hah.social.model.entity.Story;
import com.hah.social.model.entity.User;
import com.hah.social.repository.StoryRepository;
import com.hah.social.service.StoryService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;

    @Override
    public Story createStory(Story story, User user) {

        Story createdStory = new Story();
        createdStory.setCaptions(story.getCaptions());
        createdStory.setUser(user);
        createdStory.setImage(story.getImage());
        return storyRepository.save(createdStory);
    }

    @Override
    public List<Story> findStoryByUserId(Long userId) {
        userService.findUserById(userId);
        return storyRepository.findByUserId(userId);
    }
}

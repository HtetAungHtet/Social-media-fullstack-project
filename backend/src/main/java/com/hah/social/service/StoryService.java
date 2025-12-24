package com.hah.social.service;

import com.hah.social.model.entity.Story;
import com.hah.social.model.entity.User;

import java.util.List;

public interface StoryService {

    Story createStory(Story story, User user);

    List<Story> findStoryByUserId(Long userId);
}

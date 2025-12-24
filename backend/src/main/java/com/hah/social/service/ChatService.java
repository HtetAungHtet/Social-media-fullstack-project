package com.hah.social.service;

import com.hah.social.model.entity.Chat;
import com.hah.social.model.entity.User;

import java.util.List;

public interface ChatService {

    Chat createChat(User reqUser, User user2);

    Chat findChatById(Long chatId) throws Exception;

    List<Chat> findUserChat(Long userId);

}

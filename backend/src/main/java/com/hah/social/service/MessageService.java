package com.hah.social.service;

import com.hah.social.model.entity.Message;
import com.hah.social.model.entity.User;

import java.util.List;

public interface MessageService {

    Message createMessage(User user, Message req, Long chatId) throws Exception;

    List<Message> findChatMessages(Long chatId) throws Exception;
}

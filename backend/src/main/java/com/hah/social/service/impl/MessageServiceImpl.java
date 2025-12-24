package com.hah.social.service.impl;

import com.hah.social.model.entity.Chat;
import com.hah.social.model.entity.Message;
import com.hah.social.model.entity.User;
import com.hah.social.repository.ChatRepository;
import com.hah.social.repository.MessageRepository;
import com.hah.social.service.ChatService;
import com.hah.social.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatService chatService;

    @Override
    public Message createMessage(User user, Message req, Long chatId) throws Exception {
        Chat chat = chatService.findChatById(chatId);

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setImage(req.getImage());

        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);

        return savedMessage;
    }

    @Override
    public List<Message> findChatMessages(Long chatId) throws Exception {
        Chat chat = chatService.findChatById(chatId);

        return messageRepository.findByChatId(chatId);
    }
}

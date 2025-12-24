package com.hah.social.service.impl;

import com.hah.social.model.entity.Chat;
import com.hah.social.model.entity.User;
import com.hah.social.repository.ChatRepository;
import com.hah.social.service.ChatService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    public Chat createChat(User reqUser, User user2) {
        Chat isExist = chatRepository.findChatByUserId(reqUser, user2);

        if (isExist != null)
            return isExist;

        Chat chat = new Chat();
        chat.getUsers().add(reqUser);
        chat.getUsers().add(user2);
        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Long chatId) throws Exception {
        return chatRepository.findById(chatId)
                .orElseThrow(()-> new Exception("Chat not found with this id : " + chatId));
    }

    @Override
    public List<Chat> findUserChat(Long userId) {
        userService.findUserById(userId);
        return chatRepository.findByUsersId(userId);
    }
}

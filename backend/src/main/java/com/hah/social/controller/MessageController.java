package com.hah.social.controller;

import com.hah.social.model.entity.Chat;
import com.hah.social.model.entity.Message;
import com.hah.social.model.entity.User;
import com.hah.social.service.MessageService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @PostMapping("/chat/{chatId}")
    public Message createMessage(@RequestHeader("Authorization") String jwt,
                                 @RequestBody Message message,
                                 @PathVariable Long chatId) throws Exception {

        User reqUser = userService.findUserByToken(jwt);

        return messageService.createMessage(reqUser, message, chatId);
    }

    @GetMapping("/chat/{chatId}")
    public List<Message> findChatMessages(@RequestHeader("Authorization") String jwt,
                                          @PathVariable Long chatId) throws Exception {

        User reqUser = userService.findUserByToken(jwt);

        return messageService.findChatMessages(chatId);
    }
}

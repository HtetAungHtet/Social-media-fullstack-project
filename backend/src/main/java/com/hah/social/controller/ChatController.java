package com.hah.social.controller;


import com.hah.social.config.request.CreateChatRequest;
import com.hah.social.model.entity.Chat;
import com.hah.social.model.entity.User;
import com.hah.social.service.ChatService;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @PostMapping
    public Chat createChat(@RequestHeader("Authorization") String jwt,
                           @RequestBody CreateChatRequest chatRequest) {
        User reqUser = userService.findUserByToken(jwt);
        User user2 = userService.findUserById(chatRequest.getUserId()).orElseThrow();

        return chatService.createChat(reqUser, user2);
    }

    @GetMapping
    public List<Chat> findUserChat(@RequestHeader("Authorization") String jwt) {
        User reqUser = userService.findUserByToken(jwt);

        return chatService.findUserChat(reqUser.getId());
    }

    @GetMapping("/{chatId}")
    public Chat findChatById(@PathVariable Long chatId) throws Exception {
        return chatService.findChatById(chatId);
    }
}

package com.vlad.server.controllers;

import com.vlad.server.dto.MessageDTO;
import com.vlad.server.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public MessageDTO sendMessage(@DestinationVariable("chatId") String chatId, @Payload MessageDTO message) {
        return chatService.saveMessageToChat(message, Long.parseLong(chatId));
    }
}

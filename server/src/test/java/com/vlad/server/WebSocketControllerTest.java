package com.vlad.server;

import com.vlad.server.controllers.WebSocketController;
import com.vlad.server.dto.MessageDTO;
import com.vlad.server.service.chat.ChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSocketControllerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @InjectMocks
    private WebSocketController webSocketController;

    @Test
    void sendMessage() {

        String chatId = "1";
        MessageDTO messageDTO = new MessageDTO();
        when(chatService.saveMessageToChat(any(MessageDTO.class), anyLong())).thenReturn(messageDTO);

        webSocketController.sendMessage(chatId, messageDTO);

        // Then
        verify(chatService, times(1)).saveMessageToChat(any(MessageDTO.class), anyLong());
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/chat/1"), eq(messageDTO), any(MessageHeaders.class));
    }
}

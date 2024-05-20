package com.vlad.server.service.chat;

import com.vlad.server.dto.ChatDTO;
import com.vlad.server.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {
    ChatDTO getChat(Long id);
    List<MessageDTO> getMessages(Long id);
    ChatDTO createChat(List<Long> userIds, Boolean isGroup);
    MessageDTO saveMessageToChat(MessageDTO message, Long chatId);
}

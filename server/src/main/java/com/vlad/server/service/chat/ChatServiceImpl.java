package com.vlad.server.service.chat;

import com.vlad.server.dto.ChatDTO;
import com.vlad.server.dto.MessageDTO;
import com.vlad.server.entity.Chat;
import com.vlad.server.entity.Message;
import com.vlad.server.entity.User;
import com.vlad.server.repo.chat.ChatRepo;
import com.vlad.server.repo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService{
    private final ChatRepo chatRepository;
    private final UserRepo userRepository;

    @Autowired
    public ChatServiceImpl(ChatRepo chatRepository, UserRepo userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }
    @Override
    public ChatDTO getChat(Long id) {
        Chat chat = chatRepository.findById(id);
        if (chat == null) return null;
        return new ChatDTO(chat);
    }

    @Override
    public ChatDTO createChat(List<Long> userIds, Boolean isGroup) {
        Chat chat = new Chat();
        chat.setIsGroup(isGroup);
        chat.setMessages(new ArrayList<>());

        StringBuilder name = new StringBuilder();

        List<User> users = userRepository.getUsersByIds(userIds);
        for(User user : users) {
            if(users.indexOf(user) == users.size() - 1) {
                name.append(user.getName());
            }
            else name.append(user.getName()).append(", ");
            chat.addUserToChat(user);
        }
        chat.setName(name.toString());
        chatRepository.persist(chat);
        return new ChatDTO(chat);
    }
    @Override
    public MessageDTO saveMessageToChat(MessageDTO messageDTO, Long chatId) {
        Chat chat = chatRepository.findById(chatId);
        User user = userRepository.findById(messageDTO.getSender().getId());

        Message messageToSave = new Message(user, messageDTO.getContent(), messageDTO.getDateTime(), messageDTO.getStatus());
        chat.addMessageToChat(messageToSave);
        userRepository.merge(user);
        chatRepository.merge(chat);
        return new MessageDTO(messageToSave);
    }
    @Override
    public List<MessageDTO> getMessages(Long id) {
        Chat chat = chatRepository.findById(id);
        List<Message> messages = chat.getMessages();
        if (!messages.isEmpty()) {
            return messages.stream()
                    .map(MessageDTO::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

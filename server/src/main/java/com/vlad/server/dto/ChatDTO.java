package com.vlad.server.dto;

import com.vlad.server.entity.Chat;
import com.vlad.server.entity.Message;
import com.vlad.server.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChatDTO {
    private Long id;
    private String name;
    private Boolean isGroup;
    private List<UserDTO> users;
    private List<MessageDTO> messages;

    public ChatDTO() {}

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.name = chat.getName();
        this.isGroup = chat.getIsGroup();
        this.users = chat.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
        this.messages = chat.getMessages().stream().map(MessageDTO::new).collect(Collectors.toList());
    }
}

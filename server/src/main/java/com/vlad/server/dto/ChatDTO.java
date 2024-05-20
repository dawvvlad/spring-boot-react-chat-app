package com.vlad.server.dto;

import com.vlad.server.entity.Chat;
import com.vlad.server.entity.Message;
import com.vlad.server.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDTO {
    private Long id;
    private String name;
    private Boolean isGroup;
    private List<UserDTO> users;
    private List<MessageDTO> messages;

    public void addUsers(Chat chat) {
        for(User user : chat.getUsers()) {
            this.users.add(new UserDTO(user));
        }
    }

    public void addMessages(Chat chat) {
        for(Message message : chat.getMessages()) {
            this.messages.add(new MessageDTO(message));
        }
    }

    public ChatDTO() {}

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.name = chat.getName();
        this.isGroup = chat.getIsGroup();

        if (chat.getUsers() != null) {
            this.addUsers(chat);
            this.addMessages(chat);
        }
    }
}

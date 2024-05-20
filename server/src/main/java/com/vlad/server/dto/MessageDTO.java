package com.vlad.server.dto;

import com.vlad.server.entity.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private Long id;
    private String content;
    private String dateTime;
    private UserDTO sender;
    private Boolean status;

    public MessageDTO() {}

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.sender = new UserDTO(message.getSender());
        this.status = message.getStatus();
        this.dateTime = message.getTime();
    }
}

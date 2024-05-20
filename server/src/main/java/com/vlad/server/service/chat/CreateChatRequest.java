package com.vlad.server.service.chat;

import lombok.Getter;

import java.util.List;
@Getter
public class CreateChatRequest {
    private List<Long> userIds;
    private Boolean isGroup;
}

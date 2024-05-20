package com.vlad.server.repo.chat;

import com.vlad.server.entity.Chat;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo {
    void persist(Chat chat);
    Chat findById(Long id);
    void merge(Chat chat);
}
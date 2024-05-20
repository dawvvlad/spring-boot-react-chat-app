package com.vlad.server.repo.chat;

import com.vlad.server.entity.Chat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatRepoImpl implements ChatRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void persist(Chat chat) {
        entityManager.persist(chat);
    }
    @Transactional
    @Override
    public void merge(Chat chat) {
        entityManager.merge(chat);
    }

    @Transactional
    @Override
    public Chat findById(Long id) {
        try {
            return entityManager.find(Chat.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}

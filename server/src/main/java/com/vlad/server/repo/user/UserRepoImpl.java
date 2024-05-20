package com.vlad.server.repo.user;

import com.vlad.server.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void persist(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public List<User> getAllOnlineUsers() {
        return entityManager.
                createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Transactional
    @Override
    public List<User> getUsersByIds(List<Long> userIds) {
        List<User> users = new ArrayList<>();
        for (Long id : userIds) {
            users.add(entityManager.find(User.class, id));
        }
        return users;
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public void merge(User user) {
        entityManager.merge(user);
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        return entityManager.find(User.class, username);
    }
}

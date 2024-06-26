package com.vlad.server.repo.user;

import com.vlad.server.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        List<User> users = entityManager.
                createQuery("SELECT u FROM User u", User.class)
                .getResultList();
        return !users.isEmpty() ? users : null;
    }

    @Transactional
    @Override
    public List<User> getUsersByIds(List<Long> userIds) {
        List<User> users = new ArrayList<>();
        for (Long id : userIds) {
            users.add(entityManager.find(User.class, id));
        }
        return !users.isEmpty() ? users : null;
    }

    @Transactional
    @Override
    public User findById(Long id) {
        try {
            return entityManager.find(User.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void merge(User user) {
        entityManager.merge(user);
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.login = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Если пользователь не найден, возвращаем null
        }
    }
}

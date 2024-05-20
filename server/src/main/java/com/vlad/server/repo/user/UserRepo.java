package com.vlad.server.repo.user;

import com.vlad.server.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo {
    void persist(User user);
    List<User> getAllOnlineUsers();
    List<User> getUsersByIds(List<Long> userIds);
    User findById(Long id);
    void merge(User user);
    User findByUsername(String username);
}

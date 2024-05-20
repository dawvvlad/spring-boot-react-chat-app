package com.vlad.server.service.user;

import com.vlad.server.dto.ChatDTO;
import com.vlad.server.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDTO getUser(Long id);
    UserDTO createUser(String name, String surname, String login, String password);
    List<ChatDTO> getAllChats(Long userId);
    List<UserDTO> getAllOnlineUsers();
    public void updateUserStatus(Long id, Boolean status);
}

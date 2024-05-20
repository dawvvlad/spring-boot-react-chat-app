package com.vlad.server.service.user;

import com.vlad.server.dto.ChatDTO;
import com.vlad.server.dto.UserDTO;
import com.vlad.server.entity.User;
import com.vlad.server.repo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) return null;
        return new UserDTO(user);
    }
    @Override
    public UserDTO createUser(String name, String surname, String login, String password) {
        String encodePassword = passwordEncoder.encode(password);
        User user = new User(name, surname, login, encodePassword);
        userRepository.persist(user);
        return new UserDTO(user);
    }
    @Override
    public List<ChatDTO> getAllChats(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            return user.getChats().isEmpty() ? null :
                    user.getChats().stream()
                            .map(ChatDTO::new)
                            .collect(Collectors.toList());
        }
        else return Collections.emptyList();
    }
    @Override
    public List<UserDTO> getAllOnlineUsers() {
        List<User> users = userRepository.getAllOnlineUsers();
        if(users == null) return Collections.emptyList();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }
    @Override
    public void updateUserStatus(Long id, Boolean status) {
        User user = userRepository.findById(id);
        user.setStatus(status);
    }
}

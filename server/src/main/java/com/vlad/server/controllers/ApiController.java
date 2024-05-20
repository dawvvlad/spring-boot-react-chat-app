package com.vlad.server.controllers;

import com.vlad.server.dto.ChatDTO;
import com.vlad.server.dto.MessageDTO;
import com.vlad.server.dto.UserDTO;
import com.vlad.server.entity.User;
import com.vlad.server.exception_handling.NoSuchValueException;
import com.vlad.server.service.chat.ChatService;
import com.vlad.server.service.chat.CreateChatRequest;
import com.vlad.server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public ApiController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO userDTO = userService.createUser(user.getName(), user.getSurname(), user.getLogin(), user.getPassword());
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUser(id);
        if(userDTO == null) {
            throw new NoSuchValueException("User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/users/{id}/chats")
    public ResponseEntity<List<ChatDTO>> getUserChats(@PathVariable Long id) {
        UserDTO userDTO = userService.getUser(id);
        if(userDTO == null) {
            throw new NoSuchValueException("User not found");
        }
        List<ChatDTO> chats = userService.getAllChats(id);

        System.out.println(chats);
        return ResponseEntity.status(HttpStatus.OK).body(chats);
    }
    @PostMapping("/createChat")
    public ResponseEntity<ChatDTO> createChat(@RequestBody CreateChatRequest chatReq) {
        ChatDTO chat = chatService.createChat(chatReq.getUserIds(), chatReq.getIsGroup());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(chat);
    }
    @GetMapping("/chats/{id}/messages")
    public ResponseEntity<List<MessageDTO>> getUserMessages(@PathVariable Long id) {
        ChatDTO chatDTO = chatService.getChat(id);

        if(chatDTO == null) {
            throw new NoSuchValueException("Chat not found");
        }
        List<MessageDTO> messages = chatService.getMessages(id);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
    @GetMapping("/allOnline")
    public ResponseEntity<List<UserDTO>> getAllOnlineUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllOnlineUsers());
    }
}

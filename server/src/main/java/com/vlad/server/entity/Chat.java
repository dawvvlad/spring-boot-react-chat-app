package com.vlad.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@Setter
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_group")
    private Boolean isGroup;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "roster",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messages;

    public void addMessageToChat(Message message) {
        if(messages == null) messages = new ArrayList<>();
        message.setChat(this);
        messages.add(message);
    }

    public void addUserToChat(User user) {
        if(users == null) users = new ArrayList<>();
        users.add(user);
    }

    public Chat() {}

    public Chat(String name, Boolean isGroup) {
        this.name = name;
        this.isGroup = isGroup;
    }
}
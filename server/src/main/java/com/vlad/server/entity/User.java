package com.vlad.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "role")
    private String role = "USER";

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "roster",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name =
                    "chat_id"))
    private List<Chat> chats;

    public void addChatToUser(Chat chat) {
        if(chats == null) chats = new ArrayList<>();
        chats.add(chat);
    }

    public User() {}
    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.status = false;
        this.chats = new ArrayList<>();
    }
}

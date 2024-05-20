package com.vlad.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private String time;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(name = "status")
    private Boolean status;

    public Message() {}
    public Message(User sender, String content, String time, Boolean status) {
        this.sender = sender;
        this.time = time;
        this.status = status;
        this.content = content;
    }
}
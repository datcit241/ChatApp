package com.models.messages;

import com.models.users.User;

import java.time.LocalDateTime;

/**
 * Represents a message in a conversation
 * Has date, time, person, {reactions}
 * {Type: can be texts, images or files}
 */
public class Message {
    private LocalDateTime sentAt;
    private User sender;
    private String textContent;

    public Message(User sender, String text) {
        this.sender = sender;
        this.textContent = text;
        this.sentAt = LocalDateTime.now();
    }
}

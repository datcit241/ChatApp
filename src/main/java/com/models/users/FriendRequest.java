package com.models.users;

import com.models.users.*;

import java.time.LocalDateTime;

public class FriendRequest {
    private User sender;
    private User receiver;
    private LocalDateTime sentAt;

    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.sentAt = LocalDateTime.now();
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}

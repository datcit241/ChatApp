package com.models.messages;

import com.models.files.File;
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
    private User receiver;
    private String textContent;
    private File file;

    public Message(User sender, User receiver, String text, File file) {
        this.sender = sender;
        this.receiver = receiver;
        this.textContent = text;
        this.sentAt = LocalDateTime.now();
        this.file = file;
    }
}

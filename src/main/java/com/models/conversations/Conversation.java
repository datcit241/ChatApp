package com.models.conversations;

import com.models.messages.Message;
import com.models.users.User;

import java.util.*;

/**
 * Represents a conversation
 * It can be a one to one or a group conversation
 * Has messages, participants, ...
 */
public class Conversation {
    protected List<Message> messages;
    protected List<User> participants;
    protected Map<String, String> nicknames; // user id -> nickname
    protected String id;

    public Conversation() {
        messages = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public void receiveMessage(Message message) {
        messages.add(message);
    }
}

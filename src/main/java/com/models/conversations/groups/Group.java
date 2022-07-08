package com.models.conversations.groups;

import com.models.conversations.Conversation;
import com.models.users.User;

import java.util.*;

public class Group extends Conversation {
    public static final int ADDED_SUCCESSFULLY = 0;
    public static final int USER_ALREADY_IN_GROUP = 1;
    private HashMap<String, String> participantsToTheirInviters = new HashMap<>();

    public Group(ArrayList<User> participants) {
        this.participants = participants;
    }

    public boolean addUser(User inviter, User candidate) {
        this.participantsToTheirInviters.put(inviter.getUsername(), candidate.getUsername());
        return true;
    }

}

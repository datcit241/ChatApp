package com.models.conversations.groups;

import com.models.conversations.Conversation;
import com.models.users.User;

import java.util.ArrayList;

public class Group extends Conversation {
    private ArrayList<User> users;
    private String id;

    public Group() {
        users = new ArrayList<>();
    }

    public Group(ArrayList<User> users) {
        this.users = users;
    }

    public boolean addUser(User user) {
        // Check if user is in this group or user is null
        if (user == null) {
            return false;
        }
        this.users.add(user);

        return true;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }
}

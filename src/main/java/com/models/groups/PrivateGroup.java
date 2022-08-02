package com.models.groups;

import com.models.users.User;

import java.util.List;

public class PrivateGroup extends Group {
    private User admin;
    public PrivateGroup(String id, User creator, List<User> participants) {
        super(id, creator, participants);
        this.admin = creator;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public User getAdmin() {
        return this.admin;
    }
}

package com.models.groups;

import com.models.users.User;

import java.util.List;

public class PrivateGroup extends Group {
    private User admin;
    public PrivateGroup(String id, User creator, List<User> participants) {
        super(id, creator, participants);
        this.admin = creator;
    }

    public boolean setAdmin(User admin) {
        if (isAdmin(admin)) {
            return false;
        }

        this.admin = admin;
        return true;
    }

    public boolean isAdmin(User whoever) {
        return admin.equals(whoever);
    }

    public User getAdmin() {
        return this.admin;
    }
}

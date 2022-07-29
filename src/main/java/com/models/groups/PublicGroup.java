package com.models.groups;

import com.models.users.User;

import java.util.List;

public class PublicGroup extends Group {
    private String accessCode;

    public PublicGroup(String id, User creator, List<User> participants) {
        super(id, creator, participants);
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}

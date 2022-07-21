package com.models.conversations.groups;

import com.models.users.User;

import java.util.List;

public class PublicGroup extends Group {
    private String accessCode;

    public PublicGroup(String accessCode){
        super();
        this.accessCode=accessCode;
    }

    public PublicGroup(String accessCode, List<User> participants){
        this.accessCode=accessCode;
        this.participants = participants;
    }
}

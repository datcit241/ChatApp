package com.models.conversations.groups;

import com.models.users.User;

import java.util.List;

public class PrivateGroup extends Group {


    public PrivateGroup(){
        super();
    }

    public PrivateGroup( List<User> participants){
        this.participants = participants;
    }
}

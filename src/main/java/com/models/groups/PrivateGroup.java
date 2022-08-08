package com.models.groups;

import com.enums.GroupMemberRole;
import com.models.users.User;

import java.util.List;

public class PrivateGroup extends Group {

    public PrivateGroup(String id, User creator, List<User> members) {
        super(id, creator, members);
    }

    public void setAdmin(User user) {
        this.setRole(user, GroupMemberRole.Admin);
    }

    public void disposeAsAdmin(User user) {
        this.setRole(user, GroupMemberRole.Member);
    }

}

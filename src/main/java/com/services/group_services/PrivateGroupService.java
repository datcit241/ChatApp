package com.services.group_services;

import com.enums.GroupMemberRole;
import com.models.groups.PrivateGroup;
import com.models.users.User;

public class PrivateGroupService {

    public boolean setAdmin(PrivateGroup group, User whoever) {
        if (group.getRole(whoever) == GroupMemberRole.Admin) {
            return false;
        }

        return true;
    }

    public boolean isAdmin(PrivateGroup group, User whoever) {
        return group.getRole(whoever) == GroupMemberRole.Admin;
    }
}

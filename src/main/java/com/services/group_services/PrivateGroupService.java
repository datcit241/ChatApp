package com.services.group_services;

import com.models.groups.PrivateGroup;
import com.models.users.User;

public class PrivateGroupService {

    public boolean setAdmin(PrivateGroup group, User admin) {
        if (group.getAdmin().equals(admin)) {
            return false;
        }

        group.setAdmin(admin);
        return true;
    }

    public boolean isAdmin(PrivateGroup group, User whoever) {
        User admin = group.getAdmin();
        return admin.equals(whoever);
    }
}

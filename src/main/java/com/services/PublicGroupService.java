package com.services;

import com.data.DataStorage;
import com.models.groups.Group;
import com.models.groups.PublicGroup;
import com.models.users.User;

public class PublicGroupService {
    private DataStorage dataStorage = DataStorage.getDataStorage();

    public boolean joinWithAccessCode(User candidate, String accessCode) {
        Group toJoinGroup = dataStorage.getGroupRepository().find(group -> {
            if (group instanceof PublicGroup) {
                return ((PublicGroup) group).getAccessCode().equals(accessCode);
            }

            return false;
        });

        if (toJoinGroup == null) {
            return false;
        }

        toJoinGroup.addParticipant(candidate);
        return true;
    }

}

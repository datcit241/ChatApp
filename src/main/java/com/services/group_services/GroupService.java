package com.services.group_services;

import com.data.DataStorage;
import com.enums.GroupType;
import com.models.files.File;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import com.models.groups.PublicGroup;
import com.models.messages.Message;
import com.models.users.User;

import java.util.*;

public class GroupService {
    private DataStorage dataStorage;

    public GroupService() {
        dataStorage = DataStorage.getDataStorage();
    }

    public Group createGroup(GroupType groupType, User creator, List<User> members) {
        if (members.size() < 2) {
            return null;
        }

        members.add(creator);

        String groupID = UUID.randomUUID().toString();
        String accessCode = UUID.randomUUID().toString();

        Group group = switch (groupType) {
            case PrivateGroup -> new PrivateGroup(groupID, creator, members);
            default -> new PublicGroup(groupID, creator, members, accessCode);
        };

        dataStorage.getGroupRepository().insert(group);

        return group;
    }

    public boolean addMember(User member, Group group) {
        if (group.hasMember(member)) {
            return false;
        }

        group.addMember(member);
        return true;
    }

    public boolean deleteMember(User member, Group group) {
        if (group instanceof PrivateGroup) {
            boolean isAdmin = ((PrivateGroup) group).getAdmin().equals(member);

            if (isAdmin) {
                return false;
            }
        }

        if (group.hasMember(member)) {
            group.removeMember(member);
            return true;
        }

        return false;
    }

    public Iterable <File> getAllFiles(Group group) {
        Iterable<Message> messagesContainingFiles = dataStorage
                .getMessageRepository()
                .get(message -> message.getFiles() != null && message.isRelatedTo(group), Message.messageByRecentnessComparator);

        List<File> files = new ArrayList<>();
        messagesContainingFiles.forEach(message -> message.getFiles().forEach(file -> files.add(file)));

        return files;
    }

}
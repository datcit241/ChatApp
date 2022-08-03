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

    public Group createGroup(GroupType groupType, User creator, List<User> participants) {
        if (participants.size() < 2) {
            return null;
        }

        participants.add(creator);

        String groupID = UUID.randomUUID().toString();
        String accessCode = UUID.randomUUID().toString();

        Group group = switch (groupType) {
            case PrivateGroup -> new PrivateGroup(groupID, creator, participants);
            default -> new PublicGroup(groupID, creator, participants, accessCode);
        };

        dataStorage.getGroupRepository().insert(group);

        return group;
    }

    public boolean addMember(User member, Group group) {
        if (group.hasParticipant(member)) {
            return false;
        }

        group.addParticipant(member);
        return true;
    }

    public boolean deleteMember(Group group, User member) {
        if (group instanceof PrivateGroup) {
            boolean isAdmin = ((PrivateGroup) group).getAdmin().equals(member);

            if (isAdmin) {
                return false;
            }
        }

        if (group.hasParticipant(member)) {
            group.removeParticipant(member);
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
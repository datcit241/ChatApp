package com.services;

import com.data.DataStorage;
import com.models.files.File;
import com.models.groups.Group;
import com.models.messages.Message;
import com.models.users.User;

import java.util.*;

public class GroupService {
    private DataStorage dataStorage;

    public GroupService() {
        dataStorage = DataStorage.getDataStorage();
    }

    public boolean addMember(User member, Group group) {
        if (group.hasParticipant(member)) {
            return false;
        }

        group.addParticipant(member);
        return true;
    }

    public Iterable <File> getAllFiles(Group group) {
        Iterable<Message> messagesContainingFiles = dataStorage
                .getMessageRepository()
                .get(message -> message.getFile() != null && message.isRelatedTo(group), Message.messageByRecentnessComparator);

        List<File> files = new ArrayList<>();
        messagesContainingFiles.forEach(message -> files.add(message.getFile()));

        return files;
    }

}

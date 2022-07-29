package com.services;

import com.data.DataStorage;
import com.models.enums.RelationToAMessage;
import com.models.files.File;
import com.models.groups.Group;
import com.models.messages.Message;

import java.util.*;

public class GroupService {
    private DataStorage dataStorage = DataStorage.getDataStorage();

    public Iterable <File> getAllFiles(Group group) {
        Iterable<Message> messagesContainingFiles = dataStorage
                .getMessageRepository()
                .get(message -> message.getFile() != null && message.isRelatedTo(group), Message.messageByRecentnessComparator);

        List<File> files = new ArrayList<>();
        messagesContainingFiles.forEach(message -> files.add(message.getFile()));

        return files;
    }

}

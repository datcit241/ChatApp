package com.data;

import com.models.conversations.groups.Group;
import com.models.files.File;
import com.models.messages.Message;
import com.models.users.User;
import com.repositories.Repository;

public class DataStorage {
    private static DataStorage storage;

    private static Repository<User> userRepository;
    private static Repository<Group> groupRepository;
    private static Repository<Message> messageRepository;
    private static Repository<File> fileRepository;

    static {
        userRepository = new Repository<>();
        groupRepository = new Repository<>();
        messageRepository = new Repository<>();
        fileRepository = new Repository<>();
    }

    public DataStorage() {

    }

    public static DataStorage getDataStorage() {
        if (storage == null) {
            storage = new DataStorage();
        }

        return storage;
    }

    public static void setStorage(DataStorage storage) {
        DataStorage.storage = storage;
    }

    public static Repository<User> getUserRepository() {
        return userRepository;
    }

    public static void setUserRepository(Repository<User> userRepository) {
        DataStorage.userRepository = userRepository;
    }

    public static Repository<Group> getGroupRepository() {
        return groupRepository;
    }

    public static void setGroupRepository(Repository<Group> groupRepository) {
        DataStorage.groupRepository = groupRepository;
    }

    public static Repository<Message> getMessageRepository() {
        return messageRepository;
    }

  
    public static void setMessageRepository(Repository<Message> messageRepository) {
        DataStorage.messageRepository = messageRepository;
    }

    public static Repository<File> getFileRepository() {
        return fileRepository;
    }

    public static void setFileRepository(Repository<File> fileRepository) {
        DataStorage.fileRepository = fileRepository;
    }

}

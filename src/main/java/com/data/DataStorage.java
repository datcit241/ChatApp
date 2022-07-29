package com.data;

import com.models.files.File;
import com.models.groups.Group;
import com.models.messages.Message;
import com.models.users.User;
import com.repositories.Repository;

public class DataStorage {
    private static DataStorage storage;

    private Repository<User> userRepository;
    private Repository<Group> groupRepository;
    private Repository<Message> messageRepository;
    private Repository<File> fileRepository;

    private DataStorage() {

    }

    public static DataStorage getDataStorage() {
        if (storage == null) {
            storage = new DataStorage();
        }

        return storage;
    }

    public static DataStorage getStorage() {
        return storage;
    }

    public static void setStorage(DataStorage storage) {
        DataStorage.storage = storage;
    }

    public Repository<User> getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public Repository<Group> getGroupRepository() {
        return groupRepository;
    }

    public void setGroupRepository(Repository<Group> groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Repository<Message> getMessageRepository() {
        return messageRepository;
    }

    public void setMessageRepository(Repository<Message> messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Repository<File> getFileRepository() {
        return fileRepository;
    }

    public void setFileRepository(Repository<File> fileRepository) {
        this.fileRepository = fileRepository;
    }

}
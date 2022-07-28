package com.data;

import com.models.users.User;
import com.repositories.Repository;

public class DataStorage {
    private static DataStorage storage;

    private static Repository<User> userRepository;

    public DataStorage() {

    }

    public static DataStorage getDataStorage() {
        if (storage == null) {
            storage = new DataStorage();
        }

        return storage;
    }
}

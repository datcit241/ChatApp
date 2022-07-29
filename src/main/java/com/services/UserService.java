package com.services;

import com.data.DataStorage;
import com.models.enums.LoginStatus;
import com.models.users.User;
import com.utilities.HashHelper;

public class UserService {
    DataStorage dataStorage = DataStorage.getDataStorage();

    public LoginStatus login(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return LoginStatus.BlankUsernameOrPassword;
        }

        User user = dataStorage.getUserRepository().find(each -> each.getUsername().equals(username));

        if (user == null) {
            return LoginStatus.UsernameNotFound;
        }

        if (user.getHashedPassword().equals(HashHelper.hash(password))) {
            return LoginStatus.IncorrectPassword;
        }

        return LoginStatus.Successfully;
    }


}

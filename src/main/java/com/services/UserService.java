package com.services;

import com.data.DataStorage;
import com.models.enums.LoginStatus;
import com.models.messages.Message;
import com.models.users.User;
import com.utilities.HashHelper;

import java.util.Collection;

/**
 * Should add sendMessage()
 */
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

    public Iterable<Message> getLatestMessages(User user, Object conversationEntity, int k, int m) {
        final int[] count = {0};

        Iterable<Message> messagesInReverse = dataStorage
                .getMessageRepository()
                .get(message -> message.isRelatedTo(user, conversationEntity), Message.messageByRecentnessComparator);

        Iterable<Message> messagesInRange = ((Collection<Message>) messagesInReverse)
                .stream()
                .filter(message -> count[0] >= m && count[0]++ <= k)
                .toList();

        return messagesInRange;
    }

    public Iterable<Message> getMessagesContainingKeyword(User user, Object conversationEntity, String keyword) {
        Iterable<Message> messagesContainingKeyword = dataStorage
                .getMessageRepository()
                .get(message -> message.isRelatedTo(user, conversationEntity) && message.getTextContent().toLowerCase().contains(keyword.toLowerCase())
                        , Message.messageByRecentnessComparator
                );

        return messagesContainingKeyword;
    }

}

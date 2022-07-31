package com.services;

import com.data.DataStorage;
import com.models.enums.LoginStatus;
import com.models.enums.RelationToAMessage;
import com.models.friendships.Friendship;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import com.models.messages.Message;
import com.models.users.User;
import com.utilities.HashHelper;

import java.util.*;

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

    public Iterable<User> findFriends(User user) {
        Iterable<Friendship> friendships = dataStorage.getFriendshipRepository().get(friendship -> friendship.isRelatedTo(user), null);

        List<User> friends = new ArrayList<>();
        friendships.forEach(friendship -> friends.add(friendship.getFriend(user)));
        return friends;
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
        String lowerKeyword = keyword.toLowerCase();
        Iterable<Message> messagesContainingKeyword = dataStorage
                .getMessageRepository()
                .get(message -> message.isRelatedTo(user, conversationEntity) && message.getTextContent().toLowerCase().contains(lowerKeyword)
                        , Message.messageByRecentnessComparator
                );

        return messagesContainingKeyword;
    }

    public Iterable<Group> getJoinedGroups(User user) {
        Iterable<Group> groups = dataStorage.getGroupRepository().get(group -> group.hasParticipant(user), null);
        return groups;
    }

    public Iterable<User> getContacts(User user) {
        Iterable<Message> allMessages = dataStorage.getMessageRepository().get(null, null);

        Set<User> contacts = new HashSet<>();

        allMessages.forEach(message -> {
            RelationToAMessage relation = message.getRelation(user);

            if (relation != RelationToAMessage.NotRelated) {
                if (relation == RelationToAMessage.Sender) {
                    Object receiver = message.getReceiver();

                    if (receiver instanceof User) {
                        contacts.add((User) receiver);
                    }
                } else {
                    contacts.add(message.getSender());
                }
            }
        });

        return contacts;
    }

    public boolean leaveGroup(User user, Group group) {
        if (group instanceof PrivateGroup && ((PrivateGroup) group).isAdmin(user)) {
            ((PrivateGroup) group).setAdmin(null);
        }

        group.removeParticipant(user);
        return true;
    }

}

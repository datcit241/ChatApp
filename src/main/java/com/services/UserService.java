package com.services;

import com.data.DataStorage;
import com.enums.FileType;
import com.enums.Gender;
import com.enums.LoginStatus;
import com.enums.RelationToAMessage;
import com.models.files.File;
import com.models.friendships.Friendship;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import com.models.messages.Message;
import com.models.users.User;
import com.utilities.HashHelper;

import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class UserService {
    DataStorage dataStorage;

    public UserService() {
        dataStorage = DataStorage.getDataStorage();
    }

    public boolean addUser(String username, String password, String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        User userWithThisUsername = dataStorage.getUserRepository().find(user -> user.getUsername().equals(username));

        if (userWithThisUsername != null) {
            return false;
        }

        User toAddUser = new User(username, password, firstName, lastName, gender, dateOfBirth);
        dataStorage.getUserRepository().insert(toAddUser);

        return true;
    }

    public LoginStatus login(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return LoginStatus.BlankUsernameOrPassword;
        }

        User user = dataStorage.getUserRepository().find(each -> each.getUsername().equals(username));

        if (user == null) {
            return LoginStatus.UsernameNotFound;
        }

        if (!user.getHashedPassword().equals(HashHelper.hash(password))) {
            return LoginStatus.IncorrectPassword;
        }

        return LoginStatus.Successfully;
    }

    public boolean addFriend(User user, User theirFriend) {
        Friendship theirFriendship = dataStorage.getFriendshipRepository().find(friendship -> friendship.isRelatedTo(user, theirFriend));

        if (theirFriendship != null) {
            return false;
        }

        Friendship friendship = new Friendship(user, theirFriend);
        dataStorage.getFriendshipRepository().insert(friendship);

        return true;
    }

    public boolean sendMessage(User sender, Object receiver, String text, FileType fileType, Part filePart) {
        if (text == null && filePart == null) {
            return false;
        }

        File file = null;

        if (filePart != null) {
            try {
                file = new FileService().createFile(fileType, filePart);
            } catch (IOException e) {
                return false;
            }
        }

        Message message = new Message(sender, receiver, text, file);
        dataStorage.getMessageRepository().insert(message);

        return true;
    }

    public boolean removeMessage(Message message) {
        if (message.getFile() != null) {
            boolean fileRemoved = new FileService().removeFile(message.getFile().getId());

            if (!fileRemoved) {
                return false;
            }
        }

        dataStorage.getMessageRepository().delete(message);

        return true;
    }

    public Iterable<User> getFriends(User user) {
        Iterable<Friendship> friendships = dataStorage.getFriendshipRepository().get(friendship -> friendship.isRelatedTo(user), null);

        List<User> friends = new ArrayList<>();
        friendships.forEach(friendship -> friends.add(friendship.getFriend(user)));
        return friends;
    }

    public Iterable<Message> getLatestMessages(User user, Object conversationEntity, int k, int m) {
        final int[] count = {-1};
        int max = k + m - 1;

        Iterable<Message> messagesInReverse = dataStorage
                .getMessageRepository()
                .get(message -> message.isRelatedTo(user, conversationEntity), Message.messageByRecentnessComparator);

        Iterable<Message> messagesInRange = ((Collection<Message>) messagesInReverse)
                .stream()
                .filter(message -> ++count[0] >= m && count[0] <= max)
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

    public Iterable<Object> getIdsOfRelatedConversationEntities(User user) {
        Iterable<Group> groups = getJoinedGroups(user);
        Iterable<User> contacts = getContacts(user);

        List conversationEntities = new ArrayList();

        groups.forEach(group -> conversationEntities.add(group));
        contacts.forEach(contact -> conversationEntities.add(contact));

        return conversationEntities;
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

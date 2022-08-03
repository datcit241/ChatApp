package com.services;

import com.data.DataStorage;
import com.enums.FileType;
import com.enums.Gender;
import com.enums.LoginStatus;
import com.enums.RelationToAMessage;
import com.models.alias.Alias;
import com.models.files.File;
import com.models.friendships.Friendship;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import com.models.messages.Message;
import com.models.users.User;
import com.services.group_services.PrivateGroupService;
import com.utilities.HashHelper;
import com.utilities.KeywordEvaluation;

import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class UserService {
    DataStorage dataStorage;
    KeywordEvaluation keywordEvaluation;

    public UserService() {
        dataStorage = DataStorage.getDataStorage();
        keywordEvaluation = new KeywordEvaluation();
    }

    private static final String regularExpression = "^[a-zA-Z][a-zA-Z0-9_]{6,19}$";

    public boolean addUser(String username, String password, String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        if (validateUserInfo(username, password, firstName, lastName, gender, dateOfBirth)) {
            return false;
        }

        User userWithThisUsername = dataStorage.getUserRepository().find(user -> user.getUsername().equals(username));

        if (userWithThisUsername != null) {
            return false;
        }

        User toAddUser = new User(username, password, firstName, lastName, gender, dateOfBirth);
        dataStorage.getUserRepository().insert(toAddUser);

        return true;
    }

    public boolean validateUserInfo(String username, String password, String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        if (username.equals("") || password.equals("") || firstName.equals("") || lastName.equals("")) {
            return false;
        }

        for (char c : (firstName + " " + lastName).toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != ' ') {
                return false;
            }
        }

        if (!username.matches(regularExpression) || password.matches(regularExpression)) {
            return false;
        }

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

        String hashedPassword = HashHelper.hash(password);

        if (!user.getHashedPassword().equals(hashedPassword)) {
            return LoginStatus.IncorrectPassword;
        }

        return LoginStatus.Successfully;
    }

    public User findUserWithUsername(String username) {
        User user = dataStorage.getUserRepository().find(whoever -> whoever.getUsername().contains(username));

        return user;
    }

    public Iterable<User> findUserWithName(String name) {
        Iterable<User> candidates = dataStorage.getUserRepository().get(whoever -> keywordEvaluation.containsKeywords(whoever.getFullName(), name), null);

        return candidates;
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

    public boolean areFriends(User user, User whoever) {
        Friendship ifAny = dataStorage.getFriendshipRepository().find(anyFriendship -> anyFriendship.isRelatedTo(user, whoever));

        return ifAny != null;
    }

    public boolean sendMessage(User sender, Object receiver, String text, FileType fileType, List<Part> fileParts) {
        if (!(receiver instanceof Group || receiver instanceof User)) {
            return false;
        }

        if (text == null && fileParts == null) {
            return false;
        }

        if (receiver instanceof Group) {
            boolean joined = ((Group) receiver).hasParticipant(sender);

            if (!joined) {
                return false;
            }
        }

        List<File> files = new ArrayList<>();

        if (fileParts != null) {
            FileService fileService = new FileService();

            fileParts.forEach(filePart -> {
                try {
                    files.add(fileService.createFile(fileType, filePart));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        Message message = new Message(sender, receiver, text, files);
        dataStorage.getMessageRepository().insert(message);

        return true;
    }

    public boolean removeMessage(Message message) {
        if (message.getFiles() != null) {
            FileService fileService = new FileService();

            for (File file : message.getFiles()) {
                boolean fileRemoved = fileService.removeFile(file);

                if (!fileRemoved) {
                    return false;
                }
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
        String finalKeyword = keyword.toLowerCase();

        Iterable<Message> messagesContainingKeyword = dataStorage
                .getMessageRepository()
                .get(message -> message.isRelatedTo(user, conversationEntity) && keywordEvaluation.containsKeywords(message.getTextContent(), finalKeyword)
                        , Message.messageByRecentnessComparator
                );

        return messagesContainingKeyword;
    }

    public Iterable<Object> getRelatedConversationEntities(User user) {
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

        return new ArrayList<>(contacts);
    }

    public boolean leaveGroup(User user, Group group) {
        if (group instanceof PrivateGroup && new PrivateGroupService().isAdmin((PrivateGroup) group, user)) {
            return false;
        }

        group.removeParticipant(user);
        return true;
    }

    public void setAlias(User assigner, User assignee, String alias) {
        Alias oldAlias = dataStorage.getAliasRepository().find(anyAlias -> anyAlias.getAssigner().equals(assigner) && anyAlias.getAssignee().equals(assignee));

        if (oldAlias != null) {
            oldAlias.setAlias(alias);
        } else {
            Alias newAlias = new Alias(assigner, assignee, alias);
            dataStorage.getAliasRepository().insert(newAlias);
        }
    }

    public String getAnotherPersonName(User user, User whoever) {
        Alias alias = dataStorage.getAliasRepository().find(anyAlias -> anyAlias.getAssigner().equals(user) && anyAlias.getAssignee().equals(whoever));

        if (alias == null) {
            return whoever.getFullName();
        } else {
            return alias.getAlias();
        }
    }

}
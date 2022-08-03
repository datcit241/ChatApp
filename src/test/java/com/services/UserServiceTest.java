package com.services;

import com.data.DataStorage;
import com.data.seeder.DataSeeder;
import com.data.seeder.DataSeederInterface;
import com.enums.Gender;
import com.enums.LoginStatus;
import com.models.groups.Group;
import com.models.messages.Message;
import com.models.users.User;
import com.services.group_services.GroupService;
import com.utilities.HashHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static DataStorage dataStorage;
    static DataSeederInterface dataSeeder;
    static UserService userService;
    static GroupService groupService;
    static List<User> users;

    @BeforeAll
    static void setUp() {
        dataStorage = DataStorage.getDataStorage();
        dataSeeder = DataSeeder.getDataSeeder();
        dataSeeder.run();

        userService = new UserService();
        users = (List<User>) dataStorage.getUserRepository().get(null, null);

        groupService = new GroupService();
    }

    @Test
    void addUser_ShouldAddUser() {
        int numberOfUsers = users.size();

        String username = UUID.randomUUID().toString();
        userService.addUser(username, "unhackablepassword", "Fake", "Person", Gender.Male, LocalDate.now());

        assertEquals(numberOfUsers + 1, users.size());
    }

    @Test
    void addUser_ShouldNotAddUser() {
        int numberOfUsers = users.size();

        User user = users.get(0);

        userService.addUser(user.getUsername(), "unhackablepassword", "Fake", "Person", Gender.Male, LocalDate.now());

        assertEquals(numberOfUsers, users.size());
    }

    @Test
    void validateUserInfo_ShouldBeInvalid() {
        boolean isValid = userService.validateUserInfo("", "", "", "", Gender.Male, LocalDate.now());
        assertFalse(isValid);
    }

    @Test
    void validateUserInfo_ShouldBeValid() {
        boolean isValid = userService.validateUserInfo("datprovipcute", "1234", "Dat", "Vo", Gender.Male, LocalDate.now());
        assertTrue(isValid);
    }

    @Test
    void login_ShouldBeSuccessful() {
        String username = UUID.randomUUID().toString();
        String password = "Unhackable_password1029384756";
        userService.addUser(username, password, "Dat", "Vo", Gender.Male, LocalDate.now());

        LoginStatus loginStatus = userService.login(username, password);
        assertEquals(loginStatus, LoginStatus.Successfully);
    }

    @Test
    void login_ShouldFail() {
        String username = UUID.randomUUID().toString();
        String password = "Unhackable_password1029384756";
        userService.addUser(username, password, "Dat", "Vo", Gender.Male, LocalDate.now());

        assertNotEquals(LoginStatus.Successfully, userService.login("", ""));
        assertNotEquals(LoginStatus.Successfully, userService.login("", password));
        assertNotEquals(LoginStatus.Successfully, userService.login(username, ""));
        assertNotEquals(LoginStatus.Successfully, userService.login(username, "12345"));
        assertNotEquals(LoginStatus.Successfully, userService.login("13245", "12345"));
    }

    @Test
    void findUserWithUsername() {
        String username = UUID.randomUUID().toString();
        String password = "Unhackable_password1029384756";
        String firstName = "Dat";
        String lastName = "Vo";
        Gender gender = Gender.Male;
        LocalDate dateOfBirth = LocalDate.now();

        userService.addUser(username, password, firstName, lastName, gender, dateOfBirth);

        User user = userService.findUserWithUsername(username);

        boolean isEqual = username.equals(user.getUsername()) && HashHelper.hash(password).equals(user.getHashedPassword());
        assertTrue(isEqual);
    }

    @Test
    void findUserWithUsername_ShouldNotFind() {
        String username = UUID.randomUUID().toString();
        User user = userService.findUserWithUsername(username);
        assertNull(user);
    }

    @Test
    void findUserWithName() {
        List<User> users = (List<User>) userService.findUserWithName("Fake");
        assertTrue(users.size() > 0);
    }

    @Test
    void findUserWithName_ShouldNotBeFound() {
        String uniqueName = UUID.randomUUID().toString();
        Iterable<User> users = userService.findUserWithName(uniqueName);
        assertFalse(users.iterator().hasNext());
    }

    @Test
    void addFriend() {
        User user = userService.findUserWithUsername("user0");

        List<User> friends = (List<User>) userService.getFriends(user);
        int numberOfFriends = friends.size();

        String username = UUID.randomUUID().toString();
        userService.addUser(username, "Unhackable_password1029384756", "Dat", "Vo", Gender.Male, LocalDate.now());
        User newUser = userService.findUserWithUsername(username);

        userService.addFriend(user, newUser);

        friends = (List<User>) userService.getFriends(user);

        assertEquals(numberOfFriends + 1, friends.size());
    }

    @Test
    void areFriends() {
        User user0 = userService.findUserWithUsername("user0");
        User user1 = userService.findUserWithUsername("user1");

        userService.addFriend(user0, user1);

        assertTrue(userService.areFriends(user0, user1));
    }


    @Test
    void areFriends_ShouldNotBeFriends() {
        User user = userService.findUserWithUsername("user0");

        String username = UUID.randomUUID().toString();
        userService.addUser(username, "Unhackable_password1029384756", "New", "Comer", Gender.Male, LocalDate.now());
        User newComer = userService.findUserWithUsername(username);

        assertFalse(userService.areFriends(user, newComer));
    }

    @Test
    void sendMessage_ShouldFail() {
        User user0 = userService.findUserWithUsername("user0");
        User user1 = userService.findUserWithUsername("user1");

        List<Message> messages = (List<Message>) dataStorage.getMessageRepository().get(anyMessage -> anyMessage.isRelatedTo(user0, user1), null);
        int numberOfMessages = messages.size();

        String message = "I'm hacking NASA with CSS";

        boolean sent = userService.sendMessage(user0, user1, message, null, null);

        messages = (List<Message>) dataStorage.getMessageRepository().get(anyMessage -> anyMessage.isRelatedTo(user0, user1), null);
        Message messageObj = dataStorage.getMessageRepository().find(anyMessage -> anyMessage.getTextContent().equals(message));

        assertTrue(sent);
        assertEquals(numberOfMessages + 1, messages.size());
        assertNotNull(messageObj);
    }

    @Test
    void sendMessage() {
        User user0 = userService.findUserWithUsername("user0");
        User user1 = userService.findUserWithUsername("user1");

        List<Message> messages = (List<Message>) dataStorage.getMessageRepository().get(anyMessage -> anyMessage.isRelatedTo(user0, user1), null);
        int numberOfMessages = messages.size();

        String message = "I'm hacking NASA with CSS";

        boolean sent = userService.sendMessage(user0, user1, message, null, null);

        messages = (List<Message>) dataStorage.getMessageRepository().get(anyMessage -> anyMessage.isRelatedTo(user0, user1), null);
        Message messageObj = dataStorage.getMessageRepository().find(anyMessage -> anyMessage.getTextContent().equals(message));

        assertTrue(sent);
        assertEquals(numberOfMessages + 1, messages.size());
        assertNotNull(messageObj);
    }

    @Test
    void removeMessage() {
        List<Message> messages = (List<Message>) dataStorage.getMessageRepository().get(null, null);
        int numberOfMessages = messages.size();

        Message message = messages.get(0);

        userService.removeMessage(message);

        assertEquals(numberOfMessages - 1, messages.size());
    }

    @Test
    void getFriends() {
        User user0 = userService.findUserWithUsername("user0");

        User user1 = userService.findUserWithUsername("user1");
        User user2 = userService.findUserWithUsername("user2");

        userService.addFriend(user0, user1);
        userService.addFriend(user0, user2);

        List<User> friends = (List<User>) userService.getFriends(user0);

        assertTrue(friends.contains(user1) && friends.contains(user2));
    }

    @Test
    void getLatestMessages() {
        User user0 = userService.findUserWithUsername("user0");
        User user1 = userService.findUserWithUsername("user1");

        int lastMessage = 100;

        for (int i = 1; i <= lastMessage; i++) {
            userService.sendMessage(user0, user1, "" + i, null, null);
        }

        List<Message> messages = (List<Message>) userService.getLatestMessages(user0, user1, 3, 1);

        for (int i = 99, j = 0; i > 96; i--, j++) {
            assertEquals("" + i, messages.get(j).getTextContent());
        }
    }

    @Test
    void getMessagesContainingKeyword() {
        User user2 = userService.findUserWithUsername("user2");
        User user5 = userService.findUserWithUsername("user5");

        userService.sendMessage(user2, user5, "I'm hacking NASA with CSS", null, null);
        userService.sendMessage(user2, user5, "I'm Hacking NASA with HTML", null, null);
        userService.sendMessage(user2, user5, "I'm HACKing NASA with Bootstrap CSS", null, null);

        List<Message> messages = (List<Message>) userService.getMessagesContainingKeyword(user2, user5, "hacking");
        assertEquals(3, messages.size());
    }

    @Test
    void getMessagesContainingKeyword_ThereShouldBeNoMessages() {
        User user3 = userService.findUserWithUsername("user3");
        User user6 = userService.findUserWithUsername("user6");

        userService.sendMessage(user3, user6, "I'm hacking NASA with CSS", null, null);
        userService.sendMessage(user3, user6, "I'm hacking NASA with HTML", null, null);
        userService.sendMessage(user3, user6, "I'm hacking NASA with Bootstrap CSS", null, null);

        List<Message> messages = (List<Message>) userService.getMessagesContainingKeyword(user3, user6, "WhyAmISoGoodAtCoding?");
        assertEquals(0, messages.size());
    }

    @Test
    void getRelatedConversationEntities() {
        User user0 = userService.findUserWithUsername("user0");

        User user7 = userService.findUserWithUsername("user7");
        User user8 = userService.findUserWithUsername("user8");

        Group group = ((List<Group>) userService.getJoinedGroups(user0)).get(0);

        userService.sendMessage(user0, user7, "Hello", null, null);
        userService.sendMessage(user0, user8, "Hello", null, null);

        if (group != null) {
            userService.sendMessage(user0, group, "Hello", null, null);
        }

        List relatedConversationEntities = (List) userService.getRelatedConversationEntities(user0);

        assertTrue(relatedConversationEntities.contains(user7));
        assertTrue(relatedConversationEntities.contains(user8));

        if (group != null) {
            assertTrue(relatedConversationEntities.contains(group));
        }
    }

    @Test
    void getJoinedGroups() {
        User user0 = userService.findUserWithUsername("user0");

        Group group1 = DataStorage.getDataStorage().getGroupRepository().find(group -> true);
        Group group2 = DataStorage.getDataStorage().getGroupRepository().find(group -> !group.equals(group1));

        groupService.addMember(user0, group1);
        groupService.addMember(user0, group2);

        List<Group> groups = (List<Group>) userService.getJoinedGroups(user0);

        assertTrue(groups.contains(group1));
        assertTrue(groups.contains(group2));
    }

    @Test
    void getContacts() {
        User user0 = userService.findUserWithUsername("user0");

        User user7 = userService.findUserWithUsername("user7");
        User user8 = userService.findUserWithUsername("user8");

        userService.sendMessage(user0, user7, "Hello", null, null);
        userService.sendMessage(user8, user0, "Hello", null, null);

        Collection<User> relatedConversationEntities = (Collection<User>) userService.getContacts(user0);

        assertTrue(relatedConversationEntities.contains(user7));
        assertTrue(relatedConversationEntities.contains(user8));
    }

    @Test
    void leaveGroup() {
        User user0 = dataStorage.getUserRepository().find(user -> ((List<Group>) userService.getJoinedGroups(user)).size() != 0);

        Group group = ((List<Group>) userService.getJoinedGroups(user0)).get(0);

        userService.leaveGroup(user0, group);

        assertFalse(group.hasMember(user0));

        boolean actual = ((List<Group>) userService.getJoinedGroups(user0)).contains(group);
        assertFalse(actual);
    }

    @Test
    void setAlias() {
        User anyUserHavingFriend = dataStorage.getUserRepository().find(user -> ((List<User>) userService.getFriends(user)).size() != 0);
        User hisFriend = ((List<User>) userService.getFriends(anyUserHavingFriend)).get(0);

        String whatToCallHim = userService.getAnotherPersonName(anyUserHavingFriend, hisFriend);
        userService.setAlias(anyUserHavingFriend, hisFriend, "Pro coder");
        String alias = userService.getAnotherPersonName(anyUserHavingFriend, hisFriend);

        assertEquals(whatToCallHim, hisFriend.getFullName());
        assertNotEquals(whatToCallHim, alias);
    }

    @Test
    void getAnotherPersonName() {
        User user0 = userService.findUserWithUsername("user0");
        User stranger = dataStorage.getUserRepository().find(user -> !userService.areFriends(user0, user) && !user.equals(user0));

        assertEquals(stranger.getFullName(), userService.getAnotherPersonName(user0, stranger));
    }
}
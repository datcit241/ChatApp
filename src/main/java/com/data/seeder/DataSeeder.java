package com.data.seeder;

import com.data.DataStorage;
import com.enums.Gender;
import com.enums.GroupType;
import com.models.groups.Group;
import com.models.users.User;
import com.services.FileService;
import com.services.UserService;
import com.services.group_services.GroupService;

import java.time.*;
import java.util.*;

public class DataSeeder implements DataSeederInterface {
    private static DataSeeder dataSeeder;
    private boolean seeded;

    private DataStorage dataStorage;
    private UserService userService;
    private GroupService groupService;
    private FileService fileService;

    private int maxNumberOfUsers = 100;
    private int maxNumberOfFriendships = 20;
    private int maxNumberOfGroups = 15;
    private int maxNumberOfMessages = 50;

    private Random random;

    private DataSeeder() {
        this.seeded = false;
        dataStorage = DataStorage.getDataStorage();
        this.userService = new UserService();
        this.groupService = new GroupService();
        this.fileService = new FileService();
        random = new Random();
    }

    public static DataSeeder getDataSeeder() {
        if (dataSeeder == null) {
            dataSeeder = new DataSeeder();
        }

        return dataSeeder;
    }

    @Override
    public void run() {
        if (!this.seeded) {
            this.seedUsers();
            this.seedFriendships();
            this.seedGroups();
            this.seedMessages();

            this.seeded = true;
        }
    }

    private void seedUsers() {
        for (int i = 0; i < maxNumberOfUsers; i++) {
            userService.addUser("user" + i, "unhackablepassword", "Fake", "Person " + i, Gender.Male, LocalDate.now());
        }
    }

    private void seedFriendships() {
        for (int i = 0; i < maxNumberOfFriendships; i++) {
            User user = userService.findUserWithUsername("user" + random.nextInt(maxNumberOfUsers));

            while (!userService.addFriend(user, userService.findUserWithUsername("user" + random.nextInt(maxNumberOfUsers))));
        }
    }

    private void seedGroups() {
        for (int i = 0; i < maxNumberOfGroups; i++) {
            int numberOfMembers = random.nextInt(maxNumberOfUsers / 10 + 2);

            Set<User> userSet = new HashSet<>();

            for (int j = 0; j < numberOfMembers; j++) {
                userSet.add(userService.findUserWithUsername("user" + random.nextInt(maxNumberOfUsers)));
            }

            User creator;

            while (userSet.contains(creator = userService.findUserWithUsername("user" + random.nextInt(maxNumberOfUsers))));

            groupService.createGroup(((i & 1) == 0 ? GroupType.PrivateGroup : GroupType.PublicGroup), creator, new ArrayList<>(userSet));
        }
    }

    private void seedMessages() {
        List<Group> groups = (ArrayList<Group>) dataStorage.getGroupRepository().get(null, null);
        int numberOfGroups = groups.size();

        for (int i = 0; i < maxNumberOfMessages; i++) {
            int option = random.nextInt(2);

            User sender = userService.findUserWithUsername("user" + random.nextInt(maxNumberOfUsers));

            User receiverPerson;
            while (sender.equals(receiverPerson = userService.findUserWithUsername("user" + random.nextInt(maxNumberOfUsers))));

            Object receiver = switch (option) {
                case 0 -> groups.get(random.nextInt(numberOfGroups));
                default -> receiverPerson;
            };

            String text = randomizeString();

            userService.sendMessage(sender, receiver, text, null, null);
        }
    }

    private String randomizeString() {
        UUID randomUUID = UUID.randomUUID();

        return randomUUID.toString().replaceAll("_", "");
    }

    public int getMaxNumberOfUsers() {
        return maxNumberOfUsers;
    }

    public void setMaxNumberOfUsers(int maxNumberOfUsers) {
        this.maxNumberOfUsers = maxNumberOfUsers;
    }

    public int getMaxNumberOfFriendships() {
        return maxNumberOfFriendships;
    }

    public void setMaxNumberOfFriendships(int maxNumberOfFriendships) {
        this.maxNumberOfFriendships = maxNumberOfFriendships;
    }

    public int getMaxNumberOfGroups() {
        return maxNumberOfGroups;
    }

    public void setMaxNumberOfGroups(int maxNumberOfGroups) {
        this.maxNumberOfGroups = maxNumberOfGroups;
    }

    public int getMaxNumberOfMessages() {
        return maxNumberOfMessages;
    }

    public void setMaxNumberOfMessages(int maxNumberOfMessages) {
        this.maxNumberOfMessages = maxNumberOfMessages;
    }

}

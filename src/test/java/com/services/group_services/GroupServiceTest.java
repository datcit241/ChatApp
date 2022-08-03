package com.services.group_services;

import com.data.DataStorage;
import com.data.seeder.DataSeeder;
import com.data.seeder.DataSeederInterface;
import com.enums.GroupType;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import com.models.groups.PublicGroup;
import com.models.users.User;
import com.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest {
    static DataStorage dataStorage;
    static DataSeederInterface dataSeeder;
    static UserService userService;
    static GroupService groupService;
    static List<Group> groups;

    @BeforeAll
    static void setUp() {
        dataStorage = DataStorage.getDataStorage();

        dataSeeder = DataSeeder.getDataSeeder();
        dataSeeder.run();

        userService = new UserService();
        groupService = new GroupService();

        groups = (ArrayList<Group>) dataStorage.getGroupRepository().get(null, null);
    }

    @Test
    void createGroup_ShouldMatchGroupType() {
        User creator = userService.findUserWithUsername("user0");

        List<User> members = new ArrayList<>();
        members.add(userService.findUserWithUsername("user1"));
        members.add(userService.findUserWithUsername("user2"));

        Group publicGroup = groupService.createGroup(GroupType.PublicGroup, creator, members);
        Group privateGroup = groupService.createGroup(GroupType.PrivateGroup, creator, members);

        assertTrue(publicGroup instanceof PublicGroup);
        assertTrue(privateGroup instanceof PrivateGroup);
    }

    @Test
    void createGroup() {
        int numberOfGroups = groups.size();
        User creator = userService.findUserWithUsername("user0");

        List<User> members = new ArrayList<>();
        members.add(userService.findUserWithUsername("user1"));
        members.add(userService.findUserWithUsername("user2"));

        Group group = groupService.createGroup(GroupType.PublicGroup, creator, members);

        assertEquals(numberOfGroups + 1, groups.size());
    }

    @Test
    void addMember() {
        Group group = groups.get(0);
        int numberOfMembers = group.getMembers().size();

        User member = dataStorage.getUserRepository().find(user -> !group.hasMember(user));
        groupService.addMember(member, group);
        assertEquals(numberOfMembers + 1, group.getMembers().size());
    }

    @Test
    void addMember_ShouldNotAdd() {
        Group group = groups.get(0);
        int numberOfMembers = group.getMembers().size();

        User member = group.getMembers().get(0);
        groupService.addMember(member, group);
        assertEquals(numberOfMembers, group.getMembers().size());
    }

    @Test
    void deleteMember() {
        Group group = groups.get(0);
        int numberOfMembers = group.getMembers().size();

        User member = group.getMembers().get(0);
        groupService.deleteMember(member, group);
        assertEquals(numberOfMembers - 1, group.getMembers().size());
    }

}
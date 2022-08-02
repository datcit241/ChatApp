package com.services.group_services;

import com.enums.Gender;
import com.enums.GroupType;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import com.models.users.User;
import com.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrivateGroupServiceTest {
    static GroupService groupService;
    static PrivateGroupService privateGroupService;
    static UserService userService;
    static User admin;
    static User whoever;
    static Group group;

    @BeforeEach
    void setUp() {
        groupService = new GroupService();
        privateGroupService = new PrivateGroupService();
        userService = new UserService();

        userService.addUser("datprovipcute", "unhackablepassword", "Dat", "Vo", Gender.Male, LocalDate.now());
        userService.addUser("datsieucapvippro", "unhackablepassword", "Dat", "Vo", Gender.Male, LocalDate.now());
        userService.addUser("datsieuprovipcute", "unhackablepassword", "Dat", "Vo", Gender.Male, LocalDate.now());

        admin = userService.findUserWithUsername("datprovipcute");

        List<User> participants = new ArrayList<>();
        whoever = userService.findUserWithUsername("datsieucapvippro");
        participants.add(whoever);
        participants.add(userService.findUserWithUsername("datsieuprovipcute"));

        group = groupService.createGroup(GroupType.PrivateGroup, admin, participants);
    }

    @Test
    void isAdmin() {
        boolean actual = privateGroupService.isAdmin((PrivateGroup) group, admin);
        assertTrue(actual);
    }

    @Test
    void isAdmin_WhoeverShouldNotBeAnAdmin() {
        boolean actual = privateGroupService.isAdmin((PrivateGroup) group, whoever);
        assertFalse(actual);
    }

    @Test
    void setAdmin_ShouldFailToSet() {
        boolean set = privateGroupService.setAdmin((PrivateGroup) group, admin);
        assertFalse(set);
    }

    @Test
    void setAdmin() {
        boolean set = privateGroupService.setAdmin((PrivateGroup) group, whoever);
        assertTrue(set);
        assertEquals(((PrivateGroup) group).getAdmin(), whoever);
    }

}
package com.services.group_services;

import com.data.DataStorage;
import com.data.seeder.DataSeeder;
import com.data.seeder.DataSeederInterface;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrivateGroupServiceTest {
    static DataStorage dataStorage;
    static PrivateGroupService privateGroupService;
    static Group group;

    @BeforeAll
    static void setUp() {
        dataStorage = DataStorage.getDataStorage();
        privateGroupService = new PrivateGroupService();

        DataSeederInterface dataSeeder = DataSeeder.getDataSeeder();
        dataSeeder.run();

        group = dataStorage.getGroupRepository().find(group -> group instanceof PrivateGroup);
    }

    @Test
    void isAdmin() {
        boolean actual = privateGroupService.isAdmin((PrivateGroup) group, ((PrivateGroup) group).getAdmins().get(0));
        assertTrue(actual);
    }

    @Test
    void isAdmin_ShouldNotBeAnAdmin() {
        boolean actual = privateGroupService.isAdmin((PrivateGroup) group, dataStorage.getUserRepository().find(user -> !user.equals(((PrivateGroup) group).getAdmins().get(0))));
        assertFalse(actual);
    }

    @Test
    void setAdmin_ShouldFailToSet() {
        boolean set = privateGroupService.setAdmin((PrivateGroup) group, ((PrivateGroup) group).getAdmins().get(0));
        assertFalse(set);
    }

    @Test
    void setAdmin() {
        boolean set = privateGroupService.setAdmin((PrivateGroup) group, dataStorage.getUserRepository().find(user -> !user.equals(((PrivateGroup) group).getAdmins().get(0))));
        assertTrue(set);
    }

}
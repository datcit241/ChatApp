package com.services.group_services;

import com.data.DataStorage;
import com.data.seeder.DataSeeder;
import com.data.seeder.DataSeederInterface;
import com.models.groups.Group;
import com.models.groups.PublicGroup;
import com.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.models.users.User;

import static org.junit.jupiter.api.Assertions.*;

class PublicGroupServiceTest {
	static DataStorage dataStorage;
	static DataSeederInterface dataSeeder;
	static GroupService groupService;
	static PublicGroupService publicGroupService;
	static UserService userService;
	static String accessCode;
	static User userWantingToJoin;

	@BeforeAll
	public static void setup() {
		dataStorage = DataStorage.getDataStorage();
		dataSeeder = DataSeeder.getDataSeeder();

		groupService = new GroupService();
		publicGroupService = new PublicGroupService();
		userService = new UserService();

		dataSeeder.run();

		Group group = dataStorage.getGroupRepository().find(each -> each instanceof  PublicGroup);
		accessCode = ((PublicGroup) group).getAccessCode();

		userWantingToJoin = dataStorage.getUserRepository().find(user -> !group.hasParticipant(user));
	}

	@Test
	void joinWithAccessCode_ShouldNotBeAbleToJoin() {
		boolean joined = publicGroupService.joinWithAccessCode(userWantingToJoin, "");
		assertFalse(joined);
	}

	@Test
	void joinWithAccessCode_ShouldJoin() {
		boolean joined = publicGroupService.joinWithAccessCode(userWantingToJoin, accessCode);
		assertTrue(joined);
	}
}
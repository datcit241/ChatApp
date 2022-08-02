package com.services;

import com.enums.GroupType;
import com.models.groups.Group;
import com.models.groups.PublicGroup;
import com.services.group_services.GroupService;
import com.services.group_services.PublicGroupService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.enums.Gender;
import com.models.users.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class PublicGroupServiceTest {
	static GroupService groupService;
	static PublicGroupService publicGroupService;
	static UserService userService;
	static User creator;
	static String accessCode;
	static User userWantingToJoin;

	@BeforeAll
	public static void setup() {
		groupService = new GroupService();
		publicGroupService = new PublicGroupService();
		userService = new UserService();

		userService.addUser("datprovipcute", "unhackablepassword", "Dat", "Vo", Gender.Male, LocalDate.now());
		userService.addUser("datsieucapvipro", "unhackablepassword", "Dat", "Vo", Gender.Male, LocalDate.now());
		userService.addUser("datsieuprovipcute", "unhackablepassword", "Dat", "Vo", Gender.Male, LocalDate.now());
		userService.addUser("datcuteprovip", "unhackablepassword", "Dat", "Vo", Gender.Male, LocalDate.now());

		creator = userService.findUserWithUsername("datprovipcute");
		userWantingToJoin = userService.findUserWithUsername("datsieucapvippro");

		List<User> participants = new ArrayList<>();
		participants.add(userService.findUserWithUsername("datsieuprovipcute"));
		participants.add(userService.findUserWithUsername("datcuteprovip"));

		Group group = groupService.createGroup(GroupType.PublicGroup, creator, participants);

		accessCode = ((PublicGroup) group).getAccessCode();
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
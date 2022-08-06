package com.services.group_services;

import com.enums.Gender;
import com.enums.GroupType;
import com.models.groups.Group;
import com.models.groups.PrivateGroup;
import com.models.groups.PublicGroup;
import com.models.users.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.data.DataStorage;
import com.enums.FileType;
import com.models.files.File;
import com.services.FileService;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.function.Predicate;

class GroupServiceTest {
	private DataStorage dataStorage = DataStorage.getDataStorage();
	private static Group group;
	private static PrivateGroup privateGroup;
	private static PublicGroup publicGroup;
	private static GroupService groupService;
	private static List<User> participants;
	private static List<User> minorityParty;
	private static User user1;
	private static User user2;
	private static User user3;
	private static User creator;

	@BeforeAll
	static void setUp() throws Exception {
		groupService = new GroupService();
		participants = new ArrayList();
		minorityParty = new ArrayList<>();

		creator = new User("ThuanCit", "24112001", "Thuan", "Nguyen ", Gender.Male, LocalDate.of(2001, 11, 24));
		user1 = new User("Thien3t", "180220013t", "Thuan", "Nguyen Thanh ", Gender.Male, LocalDate.of(2001, 02, 18));
		user2 = new User("HuanGia", "23052001cit", "Nguyen", "Trung", Gender.Female, LocalDate.of(2001, 05, 23));
		user3 = new User("Spardon", "19331Ntt#", "Hau", "Nguyen", Gender.Male, LocalDate.of(2001, 3, 8));

		participants.add(user3);
		participants.add(user2);
		participants.add(user1);

		minorityParty.add(user3);

		group = new Group("EMA-BFJZ", creator, participants);
	}

	@Test
	void createGroup_createPrivateGroup() {
		privateGroup = (PrivateGroup) groupService.createGroup(GroupType.PrivateGroup, creator, participants);
		privateGroup.setAdmin(creator);
		User admin = privateGroup.getAdmin();

		String expectedName = "Thuan";
		String actualName = admin.getLastName();

		assertEquals(expectedName, actualName);
	}

	@Test
	void createGroup_createPublicGroup() {
		publicGroup = (PublicGroup) groupService.createGroup(GroupType.PublicGroup, user1, participants);
		publicGroup.setAccessCode("EIU Lover");

		String actual = publicGroup.getAccessCode();
		String expected = "EIU Lover";

		assertEquals(expected, actual);
	}

	@Test
	void createGroup_FailToCreate() {
		Boolean expected = false;
		Boolean actual = true;
		if (groupService.createGroup(GroupType.PublicGroup, user1, minorityParty) == null) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	void addMember_ShouldAdd() {
		User userTest = new User("Dragon", "31Ntt#", "Ha", "Ly", Gender.Other, LocalDate.of(2001, 11, 8));
		Boolean expected = true;
		Boolean actual = groupService.addMember(userTest, group);

		assertEquals(expected, actual);
	}

	@Test
	void addMember_ShouldNotAdd() {
		Boolean expected = false;
		Boolean actual = groupService.addMember(user1, group);

		assertEquals(expected, actual);
	}

	@Test
	void deleteMember_ShouldDelete() {
		Boolean expected = true;
		Boolean actual = groupService.deleteMember(group, user1);

		assertEquals(expected, actual);
	}

	@Test
	void deleteMember_ShouldNotDelete() {
		User userTest = new User("Dragon", "31Ntt#", "Ha", "Ly", Gender.Other, LocalDate.of(2001, 11, 8));
		Boolean expected = false;
		Boolean actual = groupService.deleteMember(group, userTest);

		assertEquals(expected, actual);
	}

}
package com.services;

import org.junit.jupiter.api.Test;

import com.data.DataStorage;
import com.enums.Gender;
import com.models.users.User;
import com.repositories.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;

class UserServiceTest {
	private DataStorage dataStorage = DataStorage.getDataStorage();
	private UserService userService = new UserService();
	private Repository<User> userRepository;
	static User user1;
	static User user1Friend;
	static User user2;

	@BeforeEach
	void setUp() throws Exception {
		userRepository = new Repository<>();
		user1 = new User("Spardon", "19331Ntt#", "Huan", "Nguyen", Gender.Male, LocalDate.of(2001, 3, 8));
		user1Friend = new User("DatCit", "24112001", "Tien Dat", "Vo Nguyen ", Gender.Male, LocalDate.of(2001, 11, 24));
		user2 = new User("HuanGia", "23052001cit", "Nguyen", "Trung", Gender.Female, LocalDate.of(2001, 05, 23));

		dataStorage.getUserRepository().insert(user1);
		
		

	}

	@Test
	void addUser_ShouldnotAddUser() {
		Boolean expected = false;
		Boolean actual = userService.addUser("Spardon", "19331Ntt#", "Hau", "Nguyen", Gender.Male,
				LocalDate.of(2001, 3, 8));
		assertEquals(expected, actual);
	}

	@Test
	void addUser_ShouldAddUser() {
		Boolean expected = true;
		Boolean actual = userService.addUser("RedRibbon", "19331Ntt#", "Nghia", "Nguyen", Gender.Male,
				LocalDate.of(2001, 3, 8));
		assertEquals(expected, actual);
	}

	@Test
	void login() {
	}

	@Test
	void addFriend_ShouldAddFriend() {
		boolean actual = userService.addFriend(user1, user2);
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	void addFriend_ShouldNotAddFriend() {
		boolean actual = userService.addFriend(user1, user2);
		boolean expected = false;
		assertEquals(expected, actual);

	}

	@Test
	void sendMessage() {
	}

	@Test
	void removeMessage() {
	}

	@Test
	void getFriends() {
	}

	@Test
	void getLatestMessages() {
	}

	@Test
	void getMessagesContainingKeyword() {
	}

	@Test
	void getIdsOfRelatedConversationEntities() {
	}

	@Test
	void getJoinedGroups() {
	}

	@Test
	void getContacts() {
	}

	@Test
	void leaveGroup() {
	}

	@Test
	void setAlias() {
	}

	@Test
	void getFriendName() {
	}
}
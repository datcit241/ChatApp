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

	@Test
	void login() {
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
package com.services;

import org.junit.jupiter.api.Test;

import com.data.DataStorage;
import com.enums.FileType;
import com.enums.Gender;
import com.enums.LoginStatus;
import com.models.files.File;
import com.models.messages.Message;
import com.models.users.User;
import com.repositories.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.jupiter.api.BeforeEach;

class UserServiceTest {
	private DataStorage dataStorage = DataStorage.getDataStorage();
	private UserService userService = new UserService();
	private Repository<User> userRepository;
	static User user1;
	static User user1Friend;
	static User user2;
	static Message message;
	private FileService fileService;
	static File file;

	@BeforeEach
	void setUp() throws Exception {
		userRepository = new Repository<>();
		user1 = new User("Spardon", "19331Ntt#", "Huan", "Nguyen", Gender.Male, LocalDate.of(2001, 3, 8));
		user1Friend = new User("DatCit", "24112001", "Tien Dat", "Vo Nguyen ", Gender.Male, LocalDate.of(2001, 11, 24));
		user2 = new User("HuanGia", "23052001cit", "Nguyen", "Trung", Gender.Female, LocalDate.of(2001, 05, 23));

		dataStorage.getUserRepository().insert(user1);
		dataStorage.getUserRepository().insert(user2);

		fileService = new FileService();
		file = new File(FileType.Audio, "asd123", "none", "EIU");
		dataStorage.getFileRepository().insert(file);

	}

	@Test
	void validateUserInfo_ShouldfalseBecauseOfBlank() {
		Boolean expected = false;
		Boolean actual = userService.validateUserInfo("HAU", "", "This is ", "", Gender.Other, LocalDate.now());

		assertEquals(expected, actual);
	}

	@Test
	void validateUserInfo_ShouldfalseBecauseOfExression() {
		Boolean expected = false;
		Boolean actual = userService.validateUserInfo("HAU hoang", "123", "ChocolateABC ", "Series123", Gender.Other,
				LocalDate.now());

		assertEquals(expected, actual);
	}

	@Test
	void addUser_ShouldnotAddUser() {
		Boolean expected = false;
		Boolean actual = userService.addUser("Spardon", "19331Ntt#", "Hau", "Nguyen", Gender.Male,
				LocalDate.of(2001, 3, 8));

		assertEquals(expected, actual);
	}

	void addUser_ShouldFailToAddUser() {
		Boolean expected = false;
		Boolean actual = userService.addUser("Spardon", "", "Hau", "Nguyen", Gender.Male, LocalDate.of(2001, 3, 8));

		assertEquals(expected, actual);
	}

	@Test
	void addUser_ShouldAddUser() {
		Boolean expected = true;
		Boolean actual = userService.addUser("DatVo", "2411Ntt#", "Nghia", "Nguyen", Gender.Male,
				LocalDate.of(2001, 3, 8));

		assertEquals(expected, actual);
	}

	@Test
	void login_AllBlankCAse() {
		LoginStatus actual = userService.login("", "");
		LoginStatus expected = LoginStatus.BlankUsernameOrPassword;

		assertEquals(expected, actual);
	}

	@Test
	void login_UserNameBlankCAse() {
		LoginStatus actual = userService.login("", "098765");
		LoginStatus expected = LoginStatus.BlankUsernameOrPassword;

		assertEquals(expected, actual);
	}

	@Test
	void login_PasswordBlankCAse() {
		LoginStatus actual = userService.login("zzloveAllzz", "");
		LoginStatus expected = LoginStatus.BlankUsernameOrPassword;

		assertEquals(expected, actual);
	}

	@Test
	void login_UserNamenotFound() {
		LoginStatus actual = userService.login("zzloveAllzz", "098765");
		LoginStatus expected = LoginStatus.UsernameNotFound;

		assertEquals(expected, actual);
	}

	void login_IncorreectPassword() {
		LoginStatus actual = userService.login("Spardon", "098765");
		LoginStatus expected = LoginStatus.IncorrectPassword;

		assertEquals(expected, actual);
	}

	void login_Succeess() {
		LoginStatus actual = userService.login("Spardon", "19331Ntt#");
		LoginStatus expected = LoginStatus.Successfully;

		assertEquals(expected, actual);
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
	void findUserWithUsername_ShouldSucess() {
		String expectedFullName = "Nguyen Trung";

		User actualUser = userService.findUserWithUsername("HuanGia");
		String actualFullname = actualUser.getFullName();

		assertEquals(expectedFullName, actualFullname);
	}

	@Test
	void findUserWithUsername_ShouldFail() {
		String expectedFullName = "Doesn't exist";
		String actualFullname = null;

		User actualUser = userService.findUserWithUsername("datVo");
		if (userService.findUserWithUsername("datVo") == null) {
			actualFullname = "Doesn't exist";
		} else {
			actualFullname = actualUser.getFullName();
		}
		assertEquals(expectedFullName, actualFullname);
	}

//	@Test
//	void findUserWithName_ShouldSucess() {
//		String expectedFullName = "Nguyen Trung";
//		String actual;
//
//		Iterable<User> users = userService.findUserWithName(expectedFullName);
//		Iterator it = users.iterator();
//
//
//		assertEquals(expectedFullName, actual);
//	}

}
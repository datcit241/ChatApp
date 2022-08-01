package com.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.enums.Gender;
import com.models.groups.Group;
import com.models.users.User;

import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest {

	private static Group group;
	private static List<User> participants;
	private static User user1;
	private static User user2;
	private static User user3;

	@BeforeAll
	static void setUp() throws Exception {
		participants = new ArrayList();
		User creator = new User("DatCit", "24112001", "Tien Dat", "Vo Nguyen ", Gender.Male,
				LocalDate.of(2001, 11, 24));

		user1 = new User("Thien3t", "180220013t", "Thuan", "Nguyen Thanh ", Gender.Male, LocalDate.of(2001, 02, 18));
		user2 = new User("HuanGia", "23052001cit", "Nguyen", "Trung", Gender.Female, LocalDate.of(2001, 05, 23));
		user3 = new User("Spardon", "19331Ntt#", "Hau", "Nguyen", Gender.Male, LocalDate.of(2001, 3, 8));

		participants.add(user3);
		participants.add(user2);
		participants.add(user1);

		group = new Group("EMA-BFJZ", creator, participants);
	}

	@Test 
	@DisplayName("Output of remove method should be false ")
	void removeParticipant_ShouldFailToRemove() {
		boolean expected = false;
		User testUser1 = new User("Red_Ribon", "ILoveAnime", "Nghia", "Huu", Gender.Male, LocalDate.of(2001, 11, 20));
		boolean actual = group.removeParticipant(testUser1);
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Output of remove method  should be true ")
	void remove_ShoulRemoveParticipant() {
		boolean expected = true;
		boolean actual = group.removeParticipant(user1);
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Output of add mehtod should be false ")
	void add_ShoulBeFalse() {
		boolean expected = false;
		boolean actual = group.addParticipant(user1);
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Output of add mehtod should be true ")
	void add_ShoulBeTrue() {
		User usertest = new User("dragon", "18Forever", "Hau", "Nguyen", Gender.Male, LocalDate.of(2001, 3, 8));
		boolean expected = true;
		boolean actual = group.addParticipant(user1);
		assertEquals(expected, actual);
	}

}

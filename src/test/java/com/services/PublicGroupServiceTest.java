package com.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data.DataStorage;
import com.enums.Gender;
import com.models.users.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class PublicGroupServiceTest {

	private PublicGroupService pbGroupService;
	private DataStorage dataStorage;
	
	@BeforeEach
	void setUp() {
		User userTest = new User("Spardon", "19331Ntt#", "Hau", "Nguyen", Gender.Male, LocalDate.of(2001, 3, 8));
		String accessCode = "EMA_BJZ";
		
	}

	@Test
	void joinWithAccessCode() {
		
		Boolean expected = true;
		
		
	}
}
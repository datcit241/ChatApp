package com.services.group_services;

import org.junit.jupiter.api.Test;
import com.data.DataStorage;
import com.enums.FileType;
import com.models.files.File;
import com.services.FileService;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;

class GroupServiceTest {
	private FileService fileService;
	static File file;
	private DataStorage dataStorage = DataStorage.getDataStorage();;

	@BeforeEach
	void setUp() throws Exception {
		fileService = new FileService();
		file = new File(FileType.Audio, "asd123", "none", "EIU");
		dataStorage.getFileRepository().insert(file);

	}

	@Test
	void getFileWithId_ShouldbeTrue() {
		String expectedName = "EIU";
		String actualName = fileService.getFileWithId("asd123").getName();
		assertEquals(expectedName, actualName);
	}

	@Test
	void getFileWithId_ShouldbeFalse() {
		String expectedName = "EIU";
		String actualName;

//		assertEquals(expectedName, actualName);
	}

	@Test
	void getFileWithExtension() {
	}

	@Test
	void removeFile() {
	}

	@Test
	void removePhysicalFile() {
	}
}
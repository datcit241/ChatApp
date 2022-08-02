package com.services;

import org.junit.jupiter.api.Test;

import com.data.DataStorage;
import com.enums.FileType;
import com.models.files.File;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;

class FileServiceTest {
	private FileService fileService;
	static File file;
	static File fileTest;
	static Part part;
	private DataStorage dataStorage = DataStorage.getDataStorage();;

	@BeforeEach
	void setUp() throws Exception {
		fileService = new FileService();
		file = new File(FileType.Audio, "asd123", "none", "EIU");
		dataStorage.getFileRepository().insert(file);

	}

//	@Test
//	void createFile_ShouldSuccessToCreateFile() throws IOException { 
//		fileTest = fileService.createFile(FileType.Audio, part);
//		String actual = fileTest.getExtension();
//		String expected = "";
//		assertEquals(expected, actual);
//
//	}

	@Test
	void createPhysicalFile() {

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
		String actualName = null;

		if (fileService.getFileWithId("thien3t") == null) {
			actualName = "";
		} else {
			actualName = fileService.getFileWithId("thien3t").getName();
		}
		assertNotEquals(expectedName, actualName);
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
package com.services;

import com.data.DataStorage;
import com.enums.FileType;
import com.models.files.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    static DataStorage dataStorage;
    static FileService fileService;
    static File file1;
    static File file2;
    static File file3;

    @BeforeAll
    static void setUp() {
        dataStorage = DataStorage.getDataStorage();
        fileService = new FileService();

        file1 = new File(FileType.Video, "1", "Recording of lecture 12.mp4");
        file2 = new File(FileType.Video, "2", "Why am I so good at coding.mp4");
        file3 = new File(FileType.Image, "3", "The most magnificent photo ever taken.png");

        dataStorage.getFileRepository().insert(file1);
        dataStorage.getFileRepository().insert(file2);
        dataStorage.getFileRepository().insert(file3);
    }

    @Test
    void getFileWithId() {
        File file = fileService.getFileWithId("1");

        assertEquals(file, file1);
    }

    @Test
    void getFileWithId_ShouldNotBeFound() {
        File file = fileService.getFileWithId("4");

        assertNull(file);
    }

    @Test
    void getFilesWithType() {
        List<File> videos = (List<File>) fileService.getFilesWithType(FileType.Video);
        List<File> images = (List<File>) fileService.getFilesWithType(FileType.Image);

        assertEquals(2, videos.size());
        assertEquals(1, images.size());
    }

    @Test
    void getFilesWithType_ShouldBeNoFiles() {
        List<File> audioFiles = (List<File>) fileService.getFilesWithType(FileType.Audio);

        assertEquals(0, audioFiles.size());
    }

    @Test
    void getFilesWithName_ShouldGetAll() {
        List<File> files = (List<File>) fileService.getFilesWithName(".");

        assertEquals(3, files.size());
    }

    @Test
    void getFilesWithName() {
        List<File> files = (List<File>) fileService.getFilesWithName("photo");

        assertEquals(1, files.size());
    }

}
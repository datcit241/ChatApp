package com.services;

import com.data.DataStorage;
import com.enums.FileType;

import javax.servlet.http.*;
import java.io.*;
import java.util.UUID;

public class FileService {
    private static String path;
    private DataStorage dataStorage;

    static {
        path = "resources";
    }

    public FileService() {
        dataStorage = DataStorage.getDataStorage();
    }

    public static boolean setPath(String path) {
        FileService.path = path;

        File directory = new File(path);
        if (!directory.exists()) {
            return directory.mkdirs();
        }

        return true;
    }

    public com.models.files.File createFile(FileType fileType, Part filePart) throws IOException {
        String id = UUID.randomUUID().toString();
        String name = getFileName(filePart);

        createPhysicalFile(id, filePart);

        com.models.files.File file = new com.models.files.File(fileType, id, name);
        dataStorage.getFileRepository().insert(file);

        return file;
    }

    public void createPhysicalFile(String id, Part filePart) throws IOException {
        String fileName = id;

        OutputStream out = null;
        InputStream fileContent = null;

        try {
            out = new FileOutputStream(new java.io.File(path + java.io.File.separator + fileName));
            fileContent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public com.models.files.File getFileWithId(String id) {
        com.models.files.File match = dataStorage.getFileRepository().find(file -> file.getId().equals(id));

        return match;
    }

    public Iterable<com.models.files.File> getFilesWithType(FileType fileType) {
        Iterable<com.models.files.File> files = dataStorage.getFileRepository().get(file -> file.getType() == fileType, null);
        
        return files;
    }

    public Iterable<com.models.files.File> getFilesWithName(String fileName) {
        String finalFileName = fileName.toLowerCase();
        Iterable<com.models.files.File> files = dataStorage.getFileRepository().get(file -> file.getName().toLowerCase().contains(finalFileName), null);

        return files;
    }

    public boolean removeFile(com.models.files.File file) {
        boolean physicalFileRemoved = removePhysicalFile(file.getName());

        if (file == null || !physicalFileRemoved) {
            return false;
        }

        dataStorage.getFileRepository().delete(file);
        return true;
    }

    public boolean removePhysicalFile(String fileName) {
        File file = new File(path + File.separator + fileName);

        try {
            return file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

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

    public static void setPath(String path) {
        FileService.path = path;
    }

    public void createFile(FileType fileType, Part filePart) throws IOException {
        String id = UUID.randomUUID().toString();
        String name = getFileName(filePart);
        String extension = "";

        try {
            extension = name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {

        }

        createPhysicalFile(id, filePart);

        com.models.files.File file = new com.models.files.File(fileType, id, extension, name);
        dataStorage.getFileRepository().insert(file);
    }

    public void createPhysicalFile(String id, Part filePart) throws IOException {
        String fileName = id;

        OutputStream out = null;
        InputStream filecontent = null;

        try {
            out = new FileOutputStream(new java.io.File(path + java.io.File.separator + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
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

    public com.models.files.File getFileWithExtension(String extension) {
        com.models.files.File match = dataStorage.getFileRepository().find(file -> file.getExtension().equals(extension));

        return match;
    }

    public boolean removeFile(String id) {
        com.models.files.File file = getFileWithId(id);

        boolean physicalFileRemoved = removePhysicalFile(id);

        if (file == null || physicalFileRemoved) {
            return false;
        }

        dataStorage.getFileRepository().delete(file);
        return true;
    }

    public boolean removePhysicalFile(String id) {
        File file = new File(path + File.separator + id);

        try {
            return file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

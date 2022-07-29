package com.models.files;

import com.models.enums.FileType;

public class File {
    public static String path;

    private String id;
    private FileType type;
    private String extension;
    private String name;

    public File(FileType type, String id, String extension, String name) {
        this.type = type;
        this.id = id;
        this.extension = extension;
        this.name = name;
    }

    public static void setPath(String path) {
        File.path = path;
    }

    public String getId() {
        return id;
    }

    public FileType getType() {
        return type;
    }

    public String getExtension() {
        return extension;
    }

    public String getName() {
        return name;
    }
}

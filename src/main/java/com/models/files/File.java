package com.models.files;

import com.models.enums.FileType;

public class File {
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

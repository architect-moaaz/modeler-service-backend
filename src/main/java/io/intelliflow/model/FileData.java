package io.intelliflow.model;

import java.util.UUID;

public class FileData {

    private UUID id;

    private String fileName;

    private String appName;

    private String uploadedBy;

    private String content;

    private String format;

    public FileData() {
    }

    public FileData(UUID id, String fileName, String appName, String uploadedBy, String content, String format) {
        this.id = id;
        this.fileName = fileName;
        this.appName = appName;
        this.uploadedBy = uploadedBy;
        this.content = content;
        this.format = format;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}


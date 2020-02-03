package com.changyue.blogserver.entity;

public class PostWithBLOBs extends Post {
    private String formatContent;

    private String originalContent;

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent == null ? null : formatContent.trim();
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent == null ? null : originalContent.trim();
    }
}
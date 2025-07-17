package com.clipboard.model;

import java.time.LocalDateTime;

public class ClipboardItem {
    private String content;
    private LocalDateTime timestamp;

    public ClipboardItem() {
    }

    public ClipboardItem(String content, LocalDateTime timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
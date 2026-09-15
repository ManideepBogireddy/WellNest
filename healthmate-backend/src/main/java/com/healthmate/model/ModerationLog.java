package com.healthmate.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "moderation_logs")
public class ModerationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String moderatorId;
    private String action; // APPROVE, REJECT, DELETE, REQUEST_EDIT
    private String contentType; // BLOG_POST, COMMENT
    private String contentId;
    private String notes;
    private LocalDateTime timestamp;

    public ModerationLog() {
        this.timestamp = LocalDateTime.now();
    }

    public ModerationLog(String moderatorId, String action, String contentType, String contentId, String notes) {
        this.moderatorId = moderatorId;
        this.action = action;
        this.contentType = contentType;
        this.contentId = contentId;
        this.notes = notes;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

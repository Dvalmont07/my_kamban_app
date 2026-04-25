package com.mykambanapp.domain.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain entity representing a Task inside a Kanban Column.
 * Pure Java object — no framework dependencies.
 */
@Getter
@Setter
public class Task {

    private Long id;
    private String title;
    private String description;
    private int position;
    private Column column;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(String title, String description, int position) {
        validateTitle(title);
        this.title = title;
        this.description = description;
        this.position = position;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // --- Setters ---

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    // --- Private helpers ---

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title must not be blank");
        }
    }
}

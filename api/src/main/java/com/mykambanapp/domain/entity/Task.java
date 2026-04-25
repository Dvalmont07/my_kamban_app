package com.mykambanapp.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.mykambanapp.domain.validation.TextValidator;

/**
 * Domain entity representing a Task inside a Kanban Column.
 * Pure Java object — no framework dependencies.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;
    private String title;
    private String description;
    private int position;
    private Column column;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Task(String title, String description, int position) {
        TextValidator.validateNotBlank(title, "Task title");
        this.title = title;
        this.description = description;
        this.position = position;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // --- Setters ---

    public void setTitle(String title) {
        TextValidator.validateNotBlank(title, "Task title");
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
}

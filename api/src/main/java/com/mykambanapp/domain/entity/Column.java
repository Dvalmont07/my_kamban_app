package com.mykambanapp.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain entity representing a Column inside a Kanban Board.
 * Pure Java object — no framework dependencies.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Column {

    private Long id;
    private String name;
    private int position;
    private Board board;
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Column(String name, int position) {
        validateName(name);
        this.name = name;
        this.position = position;
        this.tasks = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // --- Domain behaviour ---

    public void addTask(Task task) {
        task.setColumn(this);
        this.tasks.add(task);
        this.updatedAt = LocalDateTime.now();
    }

    public List<Task> getTasks() { return Collections.unmodifiableList(tasks); }

    // --- Setters ---

    public void setName(String name) {
        validateName(name);
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    // --- Private helpers ---

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Column name must not be blank");
        }
    }
}

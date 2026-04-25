package com.mykambanapp.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain entity representing a Kanban Board.
 * Pure Java object — no framework dependencies.
 */
@Getter
@Setter
public class Board {

    private Long id;
    private String name;
    private String description;
    private final List<Column> columns;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Board(String name, String description) {
        validateName(name);
        this.name = name;
        this.description = description;
        this.columns = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // --- Domain behaviour ---

    public void addColumn(Column column) {
        column.setBoard(this);
        this.columns.add(column);
        this.updatedAt = LocalDateTime.now();
    }

    public List<Column> getColumns() { return Collections.unmodifiableList(columns); }

    // --- Setters ---

    public void setName(String name) {
        validateName(name);
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    // --- Private helpers ---

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Board name must not be blank");
        }
    }
}

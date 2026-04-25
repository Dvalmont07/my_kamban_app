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

import com.mykambanapp.domain.validation.TextValidator;

/**
 * Domain entity representing a Kanban Board.
 * Pure Java object — no framework dependencies.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private Long id;
    private String name;
    private String description;
    @Builder.Default
    private List<Column> columns = new ArrayList<>();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Board(String name, String description) {
        TextValidator.validateNotBlank(name, "Board name");
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
        TextValidator.validateNotBlank(name, "Board name");
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
}

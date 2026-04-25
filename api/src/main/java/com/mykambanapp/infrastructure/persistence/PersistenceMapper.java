package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.domain.entity.Board;
import com.mykambanapp.domain.entity.Column;
import com.mykambanapp.domain.entity.Task;
import com.mykambanapp.infrastructure.persistence.entity.BoardJpaEntity;
import com.mykambanapp.infrastructure.persistence.entity.ColumnJpaEntity;
import com.mykambanapp.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Mapper: centralizes conversion logic between domain and JPA entities.
 * Ensures all relationships are correctly mapped.
 */
@Component
public class PersistenceMapper {

    // --- Board Mapping ---

    public BoardJpaEntity toJpa(Board board) {
        if (board == null) return null;
        
        BoardJpaEntity entity = BoardJpaEntity.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
        
        // Note: columns are mapped separately or handled via cascade
        return entity;
    }

    public Board toDomain(BoardJpaEntity entity) {
        if (entity == null) return null;

        Board board = Board.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .columns(new ArrayList<>()) // Ensure initialized
                .build();

        if (entity.getColumns() != null) {
            entity.getColumns().forEach(colEntity -> {
                Column column = toDomain(colEntity, board);
                board.addColumn(column);
            });
        }

        return board;
    }

    // --- Column Mapping ---

    public ColumnJpaEntity toJpa(Column column) {
        if (column == null) return null;

        return ColumnJpaEntity.builder()
                .id(column.getId())
                .name(column.getName())
                .position(column.getPosition())
                .createdAt(column.getCreatedAt())
                .updatedAt(column.getUpdatedAt())
                .build();
    }

    public Column toDomain(ColumnJpaEntity entity, Board board) {
        if (entity == null) return null;

        Column column = Column.builder()
                .id(entity.getId())
                .name(entity.getName())
                .position(entity.getPosition())
                .board(board)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .tasks(new ArrayList<>()) // Ensure initialized
                .build();

        if (entity.getTasks() != null) {
            entity.getTasks().forEach(taskEntity -> {
                Task task = toDomain(taskEntity, column);
                column.addTask(task);
            });
        }

        return column;
    }

    // --- Task Mapping ---

    public TaskJpaEntity toJpa(Task task) {
        if (task == null) return null;

        return TaskJpaEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPosition())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    public Task toDomain(TaskJpaEntity entity, Column column) {
        if (entity == null) return null;

        return Task.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .position(entity.getPosition())
                .column(column)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

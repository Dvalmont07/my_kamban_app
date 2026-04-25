package com.mykambanapp.domain.entity;

import com.mykambanapp.domain.entity.Board;
import com.mykambanapp.domain.entity.Column;
import com.mykambanapp.domain.entity.Task;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskTest {

    @Test
    void givenColumn_whenTaskCreated_thenTaskBelongsToColumn() {
        // GIVEN
        Board board = new Board("My Board", null);
        Column column = new Column("To Do", 0);
        board.addColumn(column);
        Task task = new Task("My Task", "Some description", 0);

        // WHEN
        column.addTask(task);

        // THEN
        assertThat(task.getColumn()).isSameAs(column);
        assertThat(column.getTasks()).hasSize(1);
    }

    @Test
    void givenNewTask_thenDescriptionIsNull() {
        // GIVEN / WHEN
        Task task = new Task("My Task", null, 0);

        // THEN
        assertThat(task.getDescription()).isNull();
        assertThat(task.getCreatedAt()).isNotNull();
        assertThat(task.getUpdatedAt()).isNotNull();
    }

    @Test
    void givenNullTitle_whenTaskCreated_thenThrowsException() {
        // GIVEN / WHEN / THEN
        assertThatThrownBy(() -> new Task(null, "desc", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("title");
    }

    @Test
    void givenBlankTitle_whenTaskCreated_thenThrowsException() {
        // GIVEN / WHEN / THEN
        assertThatThrownBy(() -> new Task("  ", "desc", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("title");
    }
}

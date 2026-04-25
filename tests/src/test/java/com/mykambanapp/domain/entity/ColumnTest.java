package com.mykambanapp.domain.entity;

import com.mykambanapp.domain.entity.Board;
import com.mykambanapp.domain.entity.Column;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnTest {

    @Test
    void givenBoard_whenColumnCreated_thenColumnBelongsToBoard() {
        // GIVEN
        Board board = new Board("My Board", null);
        Column column = new Column("To Do", 0);

        // WHEN
        board.addColumn(column);

        // THEN
        assertThat(column.getBoard()).isSameAs(board);
        assertThat(column.getPosition()).isZero();
    }

    @Test
    void givenNewColumn_thenTasksListIsEmpty() {
        // GIVEN / WHEN
        Column column = new Column("In Progress", 1);

        // THEN
        assertThat(column.getTasks()).isEmpty();
        assertThat(column.getCreatedAt()).isNotNull();
        assertThat(column.getUpdatedAt()).isNotNull();
    }

    @Test
    void givenNullName_whenColumnCreated_thenThrowsException() {
        // GIVEN / WHEN / THEN
        assertThatThrownBy(() -> new Column(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @Test
    void givenBlankName_whenColumnCreated_thenThrowsException() {
        // GIVEN / WHEN / THEN
        assertThatThrownBy(() -> new Column("  ", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }
}

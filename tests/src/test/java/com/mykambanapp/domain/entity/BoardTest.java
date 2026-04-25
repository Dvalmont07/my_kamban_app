package com.mykambanapp.domain.entity;

import com.mykambanapp.domain.entity.Board;
import com.mykambanapp.domain.entity.Column;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void givenValidName_whenBoardCreated_thenNameIsSet() {
        // GIVEN / WHEN
        Board board = new Board("My Board", "A test board");

        // THEN
        assertThat(board.getName()).isEqualTo("My Board");
        assertThat(board.getDescription()).isEqualTo("A test board");
        assertThat(board.getColumns()).isEmpty();
        assertThat(board.getCreatedAt()).isNotNull();
        assertThat(board.getUpdatedAt()).isNotNull();
    }

    @Test
    void givenNullName_whenBoardCreated_thenThrowsException() {
        // GIVEN / WHEN / THEN
        assertThatThrownBy(() -> new Board(null, "desc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @Test
    void givenBlankName_whenBoardCreated_thenThrowsException() {
        // GIVEN / WHEN / THEN
        assertThatThrownBy(() -> new Board("   ", "desc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @Test
    void givenColumn_whenAddedToBoard_thenColumnBelongsToBoard() {
        // GIVEN
        Board board = new Board("My Board", null);
        Column column = new Column("To Do", 0);

        // WHEN
        board.addColumn(column);

        // THEN
        assertThat(board.getColumns()).hasSize(1);
        assertThat(column.getBoard()).isSameAs(board);
    }
}

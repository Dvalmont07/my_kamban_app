package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.domain.entity.Board;
import com.mykambanapp.infrastructure.persistence.entity.BoardJpaEntity;
import com.mykambanapp.infrastructure.persistence.entity.ColumnJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardRepositoryAdapterTest {

    @Mock
    private SpringDataBoardRepository springDataBoardRepository;

    private PersistenceMapper mapper;
    private BoardRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mapper = new PersistenceMapper();
        adapter = new BoardRepositoryAdapter(springDataBoardRepository, mapper);
    }

    @Test
    void givenBoard_whenSave_thenReturnsMappedBoard() {
        // GIVEN
        Board board = new Board("Test Board", "Description");
        BoardJpaEntity jpaEntity = mapper.toJpa(board);
        jpaEntity.setId(1L);

        when(springDataBoardRepository.save(any(BoardJpaEntity.class))).thenReturn(jpaEntity);

        // WHEN
        Board saved = adapter.save(board);

        // THEN
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("Test Board");
        verify(springDataBoardRepository).save(any(BoardJpaEntity.class));
    }

    @Test
    void givenId_whenFindById_thenReturnsMappedBoardWithColumns() {
        // GIVEN
        Long id = 1L;
        BoardJpaEntity jpaEntity = BoardJpaEntity.builder()
                .id(id)
                .name("Full Board")
                .columns(List.of(
                        ColumnJpaEntity.builder().id(10L).name("Column 1").build()
                ))
                .build();

        when(springDataBoardRepository.findById(id)).thenReturn(Optional.of(jpaEntity));

        // WHEN
        Optional<Board> result = adapter.findById(id);

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Full Board");
        assertThat(result.get().getColumns()).hasSize(1);
        assertThat(result.get().getColumns().get(0).getName()).isEqualTo("Column 1");
    }
}

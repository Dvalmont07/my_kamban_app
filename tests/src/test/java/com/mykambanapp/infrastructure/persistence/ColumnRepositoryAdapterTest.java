package com.mykambanapp.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mykambanapp.domain.entity.Column;
import com.mykambanapp.infrastructure.persistence.entity.ColumnJpaEntity;
import com.mykambanapp.infrastructure.persistence.entity.TaskJpaEntity;

@ExtendWith(MockitoExtension.class)
class ColumnRepositoryAdapterTest {

    @Mock
    private SpringDataColumnRepository springDataColumnRepository;

    private PersistenceMapper mapper;
    private ColumnRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mapper = new PersistenceMapper();
        adapter = new ColumnRepositoryAdapter(springDataColumnRepository, mapper);
    }

    @Test
    void givenId_whenFindById_thenReturnsMappedColumnWithTasks() {
        // GIVEN
        Long id = 1L;
        ColumnJpaEntity jpaEntity = ColumnJpaEntity.builder()
                .id(id)
                .name("To Do")
                .tasks(List.of(
                        TaskJpaEntity.builder().id(100L).title("Task 1").build()
                ))
                .build();

        when(springDataColumnRepository.findById(id)).thenReturn(Optional.of(jpaEntity));

        // WHEN
        Optional<Column> result = adapter.findById(id);

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("To Do");
        assertThat(result.get().getTasks()).hasSize(1);
        assertThat(result.get().getTasks().get(0).getTitle()).isEqualTo("Task 1");
    }
}

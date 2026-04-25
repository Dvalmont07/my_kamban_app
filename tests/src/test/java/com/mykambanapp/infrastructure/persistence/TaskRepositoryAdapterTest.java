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

import com.mykambanapp.domain.entity.Task;
import com.mykambanapp.infrastructure.persistence.entity.TaskJpaEntity;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryAdapterTest {

    @Mock
    private SpringDataTaskRepository springDataTaskRepository;

    private PersistenceMapper mapper;
    private TaskRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mapper = new PersistenceMapper();
        adapter = new TaskRepositoryAdapter(springDataTaskRepository, mapper);
    }

    @Test
    void givenId_whenFindById_thenReturnsMappedTask() {
        // GIVEN
        Long id = 1L;
        TaskJpaEntity jpaEntity = TaskJpaEntity.builder()
                .id(id)
                .title("Complete Testing")
                .description("Verify everything passes")
                .position(0)
                .build();

        when(springDataTaskRepository.findById(id)).thenReturn(Optional.of(jpaEntity));

        // WHEN
        Optional<Task> result = adapter.findById(id);

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Complete Testing");
        assertThat(result.get().getDescription()).isEqualTo("Verify everything passes");
    }

    @Test
    void givenColumnId_whenFindAllByColumnId_thenReturnsMappedTasks() {
        // GIVEN
        Long columnId = 10L;
        TaskJpaEntity jpaEntity = TaskJpaEntity.builder()
                .id(1L)
                .title("Task in Column")
                .build();

        when(springDataTaskRepository.findAllByColumnId(columnId)).thenReturn(List.of(jpaEntity));

        // WHEN
        List<Task> result = adapter.findAllByColumnId(columnId);

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Task in Column");
    }
}

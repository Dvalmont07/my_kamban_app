package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.domain.entity.Task;
import com.mykambanapp.domain.repository.TaskRepository;
import com.mykambanapp.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter: implements the domain TaskRepository port using Spring Data JPA.
 */
@Component
public class TaskRepositoryAdapter implements TaskRepository {

    private final SpringDataTaskRepository springDataTaskRepository;

    public TaskRepositoryAdapter(SpringDataTaskRepository springDataTaskRepository) {
        this.springDataTaskRepository = springDataTaskRepository;
    }

    @Override
    public Task save(Task task) {
        TaskJpaEntity saved = springDataTaskRepository.save(toJpa(task));
        return toDomain(saved);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return springDataTaskRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Task> findAllByColumnId(Long columnId) {
        return springDataTaskRepository.findAllByColumnId(columnId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataTaskRepository.deleteById(id);
    }

    // --- Mapping helpers ---

    private TaskJpaEntity toJpa(Task task) {
        return TaskJpaEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPosition())
                .column(null) // column FK is managed by cascade from ColumnRepositoryAdapter
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    private Task toDomain(TaskJpaEntity entity) {
        return Task.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .position(entity.getPosition())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

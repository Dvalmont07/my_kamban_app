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
        return new TaskJpaEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPosition(),
                null, // column FK is managed by cascade from ColumnRepositoryAdapter
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private Task toDomain(TaskJpaEntity entity) {
        Task task = new Task(entity.getTitle(), entity.getDescription(), entity.getPosition());
        task.setId(entity.getId());
        task.setUpdatedAt(entity.getUpdatedAt());
        return task;
    }
}

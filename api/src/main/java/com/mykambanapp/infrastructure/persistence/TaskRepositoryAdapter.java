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
    private final PersistenceMapper mapper;

    public TaskRepositoryAdapter(SpringDataTaskRepository springDataTaskRepository, PersistenceMapper mapper) {
        this.springDataTaskRepository = springDataTaskRepository;
        this.mapper = mapper;
    }

    @Override
    public Task save(Task task) {
        TaskJpaEntity saved = springDataTaskRepository.save(mapper.toJpa(task));
        return mapper.toDomain(saved, null);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return springDataTaskRepository.findById(id).map(entity -> mapper.toDomain(entity, null));
    }

    @Override
    public List<Task> findAllByColumnId(Long columnId) {
        return springDataTaskRepository.findAllByColumnId(columnId).stream()
                .map(entity -> mapper.toDomain(entity, null))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataTaskRepository.deleteById(id);
    }
}

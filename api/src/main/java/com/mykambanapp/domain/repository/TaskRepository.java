package com.mykambanapp.domain.repository;

import com.mykambanapp.domain.entity.Task;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for Task persistence.
 * Pure Java interface — no framework dependencies.
 */
public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(Long id);
    List<Task> findAllByColumnId(Long columnId);
    void deleteById(Long id);
}

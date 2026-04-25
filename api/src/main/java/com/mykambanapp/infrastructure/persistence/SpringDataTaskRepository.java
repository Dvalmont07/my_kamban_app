package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for TaskJpaEntity.
 * Internal to infrastructure — never exposed to the domain layer.
 */
public interface SpringDataTaskRepository extends JpaRepository<TaskJpaEntity, Long> {
    List<TaskJpaEntity> findAllByColumnId(Long columnId);
}

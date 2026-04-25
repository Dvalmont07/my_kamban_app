package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.infrastructure.persistence.entity.ColumnJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for ColumnJpaEntity.
 * Internal to infrastructure — never exposed to the domain layer.
 */
public interface SpringDataColumnRepository extends JpaRepository<ColumnJpaEntity, Long> {
    List<ColumnJpaEntity> findAllByBoardId(Long boardId);
}

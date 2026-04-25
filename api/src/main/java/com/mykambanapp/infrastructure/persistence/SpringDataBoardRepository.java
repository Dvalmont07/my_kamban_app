package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.infrastructure.persistence.entity.BoardJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for BoardJpaEntity.
 * Internal to infrastructure — never exposed to the domain layer.
 */
public interface SpringDataBoardRepository extends JpaRepository<BoardJpaEntity, Long> {
}

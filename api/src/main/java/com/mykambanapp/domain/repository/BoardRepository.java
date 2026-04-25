package com.mykambanapp.domain.repository;

import com.mykambanapp.domain.entity.Board;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for Board persistence.
 * Pure Java interface — no framework dependencies.
 */
public interface BoardRepository {
    Board save(Board board);
    Optional<Board> findById(Long id);
    List<Board> findAll();
    void deleteById(Long id);
}

package com.mykambanapp.domain.repository;

import com.mykambanapp.domain.entity.Column;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for Column persistence.
 * Pure Java interface — no framework dependencies.
 */
public interface ColumnRepository {
    Column save(Column column);
    Optional<Column> findById(Long id);
    List<Column> findAllByBoardId(Long boardId);
    void deleteById(Long id);
}

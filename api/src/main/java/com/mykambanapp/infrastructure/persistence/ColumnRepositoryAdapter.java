package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.domain.entity.Column;
import com.mykambanapp.domain.repository.ColumnRepository;
import com.mykambanapp.infrastructure.persistence.entity.ColumnJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter: implements the domain ColumnRepository port using Spring Data JPA.
 */
@Component
public class ColumnRepositoryAdapter implements ColumnRepository {

    private final SpringDataColumnRepository springDataColumnRepository;

    public ColumnRepositoryAdapter(SpringDataColumnRepository springDataColumnRepository) {
        this.springDataColumnRepository = springDataColumnRepository;
    }

    @Override
    public Column save(Column column) {
        ColumnJpaEntity saved = springDataColumnRepository.save(toJpa(column));
        return toDomain(saved);
    }

    @Override
    public Optional<Column> findById(Long id) {
        return springDataColumnRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Column> findAllByBoardId(Long boardId) {
        return springDataColumnRepository.findAllByBoardId(boardId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataColumnRepository.deleteById(id);
    }

    // --- Mapping helpers ---

    private ColumnJpaEntity toJpa(Column column) {
        return ColumnJpaEntity.builder()
                .id(column.getId())
                .name(column.getName())
                .position(column.getPosition())
                .board(null) // board FK is managed by cascade from BoardRepositoryAdapter
                .createdAt(column.getCreatedAt())
                .updatedAt(column.getUpdatedAt())
                .build();
    }

    private Column toDomain(ColumnJpaEntity entity) {
        return Column.builder()
                .id(entity.getId())
                .name(entity.getName())
                .position(entity.getPosition())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

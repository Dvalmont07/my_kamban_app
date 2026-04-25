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
        return new ColumnJpaEntity(
                column.getId(),
                column.getName(),
                column.getPosition(),
                null, // board FK is managed by cascade from BoardRepositoryAdapter
                column.getCreatedAt(),
                column.getUpdatedAt()
        );
    }

    private Column toDomain(ColumnJpaEntity entity) {
        Column column = new Column(entity.getName(), entity.getPosition());
        column.setId(entity.getId());
        column.setUpdatedAt(entity.getUpdatedAt());
        return column;
    }
}

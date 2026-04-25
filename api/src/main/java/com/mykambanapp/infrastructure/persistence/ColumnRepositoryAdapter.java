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
    private final PersistenceMapper mapper;

    public ColumnRepositoryAdapter(SpringDataColumnRepository springDataColumnRepository, PersistenceMapper mapper) {
        this.springDataColumnRepository = springDataColumnRepository;
        this.mapper = mapper;
    }

    @Override
    public Column save(Column column) {
        ColumnJpaEntity saved = springDataColumnRepository.save(mapper.toJpa(column));
        return mapper.toDomain(saved, null); // Board is handled by the caller or lazy load
    }

    @Override
    public Optional<Column> findById(Long id) {
        return springDataColumnRepository.findById(id).map(entity -> mapper.toDomain(entity, null));
    }

    @Override
    public List<Column> findAllByBoardId(Long boardId) {
        return springDataColumnRepository.findAllByBoardId(boardId).stream()
                .map(entity -> mapper.toDomain(entity, null))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataColumnRepository.deleteById(id);
    }
}

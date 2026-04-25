package com.mykambanapp.infrastructure.persistence;

import com.mykambanapp.domain.entity.Board;
import com.mykambanapp.domain.repository.BoardRepository;
import com.mykambanapp.infrastructure.persistence.entity.BoardJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter: implements the domain BoardRepository port using Spring Data JPA.
 * Translates between domain Board objects and BoardJpaEntity objects using PersistenceMapper.
 */
@Component
public class BoardRepositoryAdapter implements BoardRepository {

    private final SpringDataBoardRepository springDataBoardRepository;
    private final PersistenceMapper mapper;

    public BoardRepositoryAdapter(SpringDataBoardRepository springDataBoardRepository, PersistenceMapper mapper) {
        this.springDataBoardRepository = springDataBoardRepository;
        this.mapper = mapper;
    }

    @Override
    public Board save(Board board) {
        BoardJpaEntity saved = springDataBoardRepository.save(mapper.toJpa(board));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Board> findById(Long id) {
        return springDataBoardRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Board> findAll() {
        return springDataBoardRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataBoardRepository.deleteById(id);
    }
}

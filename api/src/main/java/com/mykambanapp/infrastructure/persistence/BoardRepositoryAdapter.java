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
 * Translates between domain Board objects and BoardJpaEntity objects.
 */
@Component
public class BoardRepositoryAdapter implements BoardRepository {

    private final SpringDataBoardRepository springDataBoardRepository;

    public BoardRepositoryAdapter(SpringDataBoardRepository springDataBoardRepository) {
        this.springDataBoardRepository = springDataBoardRepository;
    }

    @Override
    public Board save(Board board) {
        BoardJpaEntity saved = springDataBoardRepository.save(toJpa(board));
        return toDomain(saved);
    }

    @Override
    public Optional<Board> findById(Long id) {
        return springDataBoardRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Board> findAll() {
        return springDataBoardRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataBoardRepository.deleteById(id);
    }

    // --- Mapping helpers ---

    private BoardJpaEntity toJpa(Board board) {
        return new BoardJpaEntity(
                board.getId(),
                board.getName(),
                board.getDescription(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }

    private Board toDomain(BoardJpaEntity entity) {
        Board board = new Board(entity.getName(), entity.getDescription());
        board.setId(entity.getId());
        board.setUpdatedAt(entity.getUpdatedAt());
        return board;
    }
}

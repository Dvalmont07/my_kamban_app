package com.mykambanapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA entity for the tasks table.
 * Lives in infrastructure — domain entities have zero JPA dependencies.
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
public class TaskJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "position", nullable = false)
    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id", nullable = false)
    private ColumnJpaEntity column;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** Required by JPA. */
    protected TaskJpaEntity() {}

    public TaskJpaEntity(Long id, String title, String description, int position,
                         ColumnJpaEntity column, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.position = position;
        this.column = column;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}

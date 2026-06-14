package com.planetaluzu.event.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Getter
@Setter
public class EventJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name= "created_at", nullable = false)
    private LocalDateTime createdAt;
}

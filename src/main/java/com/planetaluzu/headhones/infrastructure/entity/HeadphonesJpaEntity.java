package com.planetaluzu.headhones.infrastructure.entity;


import com.planetaluzu.headhones.domain.HeadphonesStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "headphones")
@Getter
@Setter
public class HeadphonesJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "headphones_id", nullable = false)
    private Long headphonesId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private HeadphonesStatus status;
}

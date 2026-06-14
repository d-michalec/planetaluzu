package com.planetaluzu.event.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Event {
    private Long id;
    private String name;
    private LocalDateTime date;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Event(String name, LocalDateTime date, BigDecimal price) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }
}

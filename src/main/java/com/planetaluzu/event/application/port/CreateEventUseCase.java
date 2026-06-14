package com.planetaluzu.event.application.port;

import com.planetaluzu.event.domain.Event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CreateEventUseCase {
    public Event execute(String name, LocalDateTime date, BigDecimal price);
}

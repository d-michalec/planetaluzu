package com.planetaluzu.auth.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateEventRequest {
    private String name;
    private LocalDateTime date;
    private BigDecimal price;
}

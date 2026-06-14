package com.planetaluzu.event.application.port;

import com.planetaluzu.event.domain.Event;

import java.util.Optional;

public interface GetCurrentEventUseCase {
    Optional<Event> execute();
}

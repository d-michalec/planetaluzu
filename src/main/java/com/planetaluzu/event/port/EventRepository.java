package com.planetaluzu.event.port;

import com.planetaluzu.event.domain.Event;

import java.util.List;

public interface EventRepository {
    Event save(Event event);
    void deleteById(Long id);
    void deleteAll();
    List<Event> findAll();
}

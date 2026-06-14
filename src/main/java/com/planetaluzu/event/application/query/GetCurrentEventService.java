package com.planetaluzu.event.application.query;

import com.planetaluzu.event.application.cache.CurrentEventCache;
import com.planetaluzu.event.application.port.GetCurrentEventUseCase;
import com.planetaluzu.event.domain.Event;
import com.planetaluzu.event.port.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCurrentEventService implements GetCurrentEventUseCase {
    private final EventRepository eventRepository;
    private final CurrentEventCache currentEventCache;

    @Override
    public Optional<Event> execute() {
        return currentEventCache.getOrLoad(this::loadCurrentEvent);
    }

    private Optional<Event> loadCurrentEvent() {
        return eventRepository.findAll().stream()
                .max(Comparator.comparing(Event::getDate));
    }
}

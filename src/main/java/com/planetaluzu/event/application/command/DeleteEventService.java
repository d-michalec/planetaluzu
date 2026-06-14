package com.planetaluzu.event.application.command;

import com.planetaluzu.event.application.cache.CurrentEventCache;
import com.planetaluzu.event.application.port.DeleteEventUseCase;
import com.planetaluzu.event.domain.Event;
import com.planetaluzu.event.port.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@RequiredArgsConstructor
@Service
public class DeleteEventService implements DeleteEventUseCase {
    private final EventRepository eventRepository;
    private final CurrentEventCache currentEventCache;

    @Override
    public void execute(Long id) {
        eventRepository.deleteById(id);
        currentEventCache.put(
                eventRepository.findAll().stream()
                        .max(Comparator.comparing(Event::getDate))
        );
    }
}

package com.planetaluzu.event.application.command;

import com.planetaluzu.common.exception.BusinessRuleException;
import com.planetaluzu.common.exception.ConflictException;
import com.planetaluzu.event.application.cache.CurrentEventCache;
import com.planetaluzu.event.application.port.CreateEventUseCase;
import com.planetaluzu.event.domain.Event;
import com.planetaluzu.event.port.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateEventService implements CreateEventUseCase {
    private final EventRepository eventRepository;
    private final CurrentEventCache currentEventCache;

    @Override
    public Event execute(String name, LocalDateTime date, BigDecimal price) {
        List<Event> events = eventRepository.findAll();
        if(!events.isEmpty()) {
            throw new ConflictException("EVENT_ALREADY_EXISTS", "Event jest już utworzony.");
        }
        if (price == null || price.signum() <= 0) {
            throw new BusinessRuleException(
                    "EVENT_PRICE_INVALID",
                    "Cena eventu musi byc wieksza od 0."
            );
        }
        Event event = new Event(name, date, price);
        Event savedEvent = eventRepository.save(event);
        currentEventCache.put(java.util.Optional.of(savedEvent));
        return savedEvent;
    }
}

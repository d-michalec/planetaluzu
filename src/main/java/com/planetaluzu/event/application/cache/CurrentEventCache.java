package com.planetaluzu.event.application.cache;

import com.planetaluzu.event.domain.Event;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component
public class CurrentEventCache {
    private boolean initialized;
    private Optional<Event> currentEvent = Optional.empty();

    public synchronized Optional<Event> getOrLoad(Supplier<Optional<Event>> loader) {
        if (!initialized) {
            currentEvent = loader.get();
            initialized = true;
        }

        return currentEvent;
    }

    public synchronized void put(Optional<Event> event) {
        currentEvent = event;
        initialized = true;
    }
}

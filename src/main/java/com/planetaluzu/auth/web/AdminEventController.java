package com.planetaluzu.auth.web;

import com.planetaluzu.auth.web.dto.CreateEventRequest;
import com.planetaluzu.event.application.port.CreateEventUseCase;
import com.planetaluzu.event.application.port.DeleteAllDataUseCase;
import com.planetaluzu.event.application.port.DeleteEventUseCase;
import com.planetaluzu.event.application.port.GetCurrentEventUseCase;
import com.planetaluzu.event.domain.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/admin/event")
@RequiredArgsConstructor
public class AdminEventController {

    private final CreateEventUseCase createEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final DeleteAllDataUseCase deleteAllDataUseCase;
    private final GetCurrentEventUseCase getCurrentEventUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Event> getCurrentEvent() {
        return getCurrentEventUseCase.execute()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Event> getCurrentEventLegacyPath() {
        return getCurrentEvent();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody CreateEventRequest request) {
        return createEventUseCase.execute(
                request.getName(),
                request.getDate(),
                request.getPrice()
        );
    }

    @DeleteMapping("/all-data")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllData() {
        deleteAllDataUseCase.execute();
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId) {
        deleteEventUseCase.execute(eventId);
    }
}

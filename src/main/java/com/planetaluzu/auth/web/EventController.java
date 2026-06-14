package com.planetaluzu.auth.web;

import com.planetaluzu.event.application.port.GetCurrentEventUseCase;
import com.planetaluzu.event.domain.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final GetCurrentEventUseCase getCurrentEventUseCase;

    @GetMapping
    public ResponseEntity<Event> getCurrentEvent() {
        return getCurrentEventUseCase.execute()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/current")
    public ResponseEntity<Event> getCurrentEventLegacyPath() {
        return getCurrentEvent();
    }
}

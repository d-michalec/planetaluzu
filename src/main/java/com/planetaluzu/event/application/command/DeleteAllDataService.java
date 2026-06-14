package com.planetaluzu.event.application.command;

import com.planetaluzu.event.application.cache.CurrentEventCache;
import com.planetaluzu.event.application.port.DeleteAllDataUseCase;
import com.planetaluzu.event.port.EventRepository;
import com.planetaluzu.headhones.application.port.DeleteAllHeadphonesUseCase;
import com.planetaluzu.registration.application.port.in.DeleteAllRegistrationsUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAllDataService implements DeleteAllDataUseCase {

    private final DeleteAllRegistrationsUseCase deleteAllRegistrationsUseCase;
    private final DeleteAllHeadphonesUseCase deleteAllHeadphonesUseCase;
    private final EventRepository eventRepository;
    private final CurrentEventCache currentEventCache;

    @Override
    @Transactional
    public void execute() {
        deleteAllRegistrationsUseCase.execute();
        deleteAllHeadphonesUseCase.execute();
        eventRepository.deleteAll();
        currentEventCache.put(java.util.Optional.empty());
    }
}

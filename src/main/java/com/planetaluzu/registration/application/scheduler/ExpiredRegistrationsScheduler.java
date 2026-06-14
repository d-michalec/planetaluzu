package com.planetaluzu.registration.application.scheduler;

import com.planetaluzu.registration.application.command.ExpireUnpaidRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpiredRegistrationsScheduler {

    private final ExpireUnpaidRegistrationService expireUnpaidRegistrationService;

    @Scheduled(cron = "0 */60 * * * *")
    public void run() {
        expireUnpaidRegistrationService.expireRegistration();
    }
}

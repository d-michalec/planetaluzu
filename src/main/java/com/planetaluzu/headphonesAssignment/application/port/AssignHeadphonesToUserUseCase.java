package com.planetaluzu.headphonesAssignment.application.port;

import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;

public interface AssignHeadphonesToUserUseCase {
    HeadphonesAssignment execute(String ticketUUID, Long headphonesId);

    HeadphonesAssignment executeByEmail(String email, Long headphonesId);
}
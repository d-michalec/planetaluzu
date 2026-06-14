package com.planetaluzu.headphonesAssignment.application.port;

import com.planetaluzu.auth.web.dto.Attendee;

import java.util.List;

public interface GetHeadphonesAssignmentUseCase {
    List<Attendee> execute();
}

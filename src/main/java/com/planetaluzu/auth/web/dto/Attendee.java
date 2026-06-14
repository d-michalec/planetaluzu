package com.planetaluzu.auth.web.dto;

import com.planetaluzu.headphonesAssignment.domain.AssignmentStatus;
import lombok.Data;

@Data
public class Attendee {
    private String firstName;
    private String lastName;
    private Long headphonesId;
    private AssignmentStatus assignmentStatus;
}

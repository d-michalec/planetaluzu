package com.planetaluzu.auth.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignHeadphonesRequest {
    @NotBlank
    private String ticketUUID;

    @NotBlank
    private Long headphonesId;
}

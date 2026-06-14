package com.planetaluzu.auth.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManualAssignHeadphonesRequest {
    private String email;
    private Long headphonesId;
}
package com.planetaluzu.auth.web.dto;

import com.planetaluzu.headhones.domain.HeadphonesStatus;
import lombok.Data;

@Data
public class EditHeadphonesRequest {
    Long headphonesId;
    HeadphonesStatus status;
}


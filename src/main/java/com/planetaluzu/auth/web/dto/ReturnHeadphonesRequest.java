package com.planetaluzu.auth.web.dto;
import com.planetaluzu.headphonesAssignment.domain.ReturnCondition;
import lombok.Data;

@Data
public class ReturnHeadphonesRequest {
    Long headphonesId;
    ReturnCondition status;
}

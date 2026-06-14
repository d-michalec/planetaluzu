package com.planetaluzu.headphonesAssignment.application.port;

import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;
import com.planetaluzu.headphonesAssignment.domain.ReturnCondition;

public interface UserReturnHeadphonesUseCase {
    HeadphonesAssignment execute(Long headphonesId, ReturnCondition status);
}

package com.planetaluzu.headhones.application.port;


import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.domain.HeadphonesStatus;

public interface EditHeadphonesUseCase {
     Headphones execute(Long headphonesId, HeadphonesStatus status);
}

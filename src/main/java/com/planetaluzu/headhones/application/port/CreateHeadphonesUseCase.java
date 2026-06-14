package com.planetaluzu.headhones.application.port;

import com.planetaluzu.headhones.domain.Headphones;

public interface CreateHeadphonesUseCase {
    Headphones execute(Long headphonesId);
}

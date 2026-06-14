package com.planetaluzu.headhones.application.port;

import com.planetaluzu.headhones.domain.Headphones;

import java.util.List;

public interface GetAllHeadphonesUseCase {
    List<Headphones> execute();
}

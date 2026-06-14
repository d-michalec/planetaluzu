package com.planetaluzu.headhones.application.query;

import com.planetaluzu.headhones.application.port.GetAllHeadphonesUseCase;
import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllHeadphonesService implements GetAllHeadphonesUseCase {
    private final HeadphonesRepository headphonesRepository;

    @Override
    public List<Headphones> execute() {
        return headphonesRepository.findAll();
    }
}

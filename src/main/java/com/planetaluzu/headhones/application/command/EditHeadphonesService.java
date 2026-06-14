package com.planetaluzu.headhones.application.command;

import com.planetaluzu.common.exception.ResourceNotFoundException;
import com.planetaluzu.headhones.application.port.EditHeadphonesUseCase;
import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.domain.HeadphonesStatus;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditHeadphonesService implements EditHeadphonesUseCase {
    private final HeadphonesRepository headphonesRepository;

    @Override
    public Headphones execute(Long headphonesId, HeadphonesStatus status) {
        Headphones headphones = headphonesRepository.findByHeadphonesId(headphonesId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "HEADPHONES_NOT_FOUND",
                        "Nie znaleziono słuchawek o ID " + headphonesId + "."
                ));
        headphones.setStatus(status);
        return headphonesRepository.save(headphones);
    }
}

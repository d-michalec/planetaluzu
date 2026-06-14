package com.planetaluzu.headhones.application.command;

import com.planetaluzu.common.exception.ConflictException;
import com.planetaluzu.headhones.application.port.CreateHeadphonesUseCase;

import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHeadphonesService implements CreateHeadphonesUseCase {
    private final HeadphonesRepository headphonesRepository;

    @Transactional
    @Override
    public Headphones execute(Long headphonesId) {
        if(headphonesRepository.findByHeadphonesId(headphonesId).isPresent()){
            throw new ConflictException(
                    "HEADPHONES_ALREADY_EXISTS",
                    "Słuchawki o ID " + headphonesId + " już istnieją."
            );
        }
        Headphones headphones = new Headphones(headphonesId);
        headphones.markAsAvailable();
        return headphonesRepository.save(headphones);
    }

}

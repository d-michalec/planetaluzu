package com.planetaluzu.headhones.application.command;

import com.planetaluzu.common.exception.ResourceNotFoundException;
import com.planetaluzu.headhones.application.port.DeleteHeadphonesUseCase;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
@RequiredArgsConstructor
public class DeleteHeadphonesService implements DeleteHeadphonesUseCase {
    private final HeadphonesRepository headphonesRepository;

    @Transactional
    @Override
    public void execute(Long headphonesId) {
        headphonesRepository.findByHeadphonesId(headphonesId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "HEADPHONES_NOT_FOUND",
                        "Nie znaleziono słuchawek o ID " + headphonesId + "."
                ));
        headphonesRepository.delete(headphonesId);
    }
}

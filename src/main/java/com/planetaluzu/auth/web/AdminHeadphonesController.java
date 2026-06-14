package com.planetaluzu.auth.web;

import com.planetaluzu.auth.web.dto.EditHeadphonesRequest;
import com.planetaluzu.headhones.application.port.CreateHeadphonesUseCase;
import com.planetaluzu.headhones.application.port.DeleteAllHeadphonesUseCase;
import com.planetaluzu.headhones.application.port.DeleteHeadphonesUseCase;
import com.planetaluzu.headhones.application.port.EditHeadphonesUseCase;
import com.planetaluzu.headhones.application.port.GetAllHeadphonesUseCase;
import com.planetaluzu.headhones.domain.Headphones;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/admin/headphones")
@RequiredArgsConstructor
public class AdminHeadphonesController {

    private final CreateHeadphonesUseCase createHeadphonesUseCase;
    private final DeleteHeadphonesUseCase deleteHeadphonesUseCase;
    private final DeleteAllHeadphonesUseCase deleteAllHeadphonesUseCase;
    private final GetAllHeadphonesUseCase getAllHeadphonesUseCase;
    private final EditHeadphonesUseCase editHeadphonesUseCase;

    @PostMapping("/{headphonesId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Headphones createHeadphones(@PathVariable Long headphonesId) {
        return createHeadphonesUseCase.execute(headphonesId);
    }

    @DeleteMapping("/{headphonesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHeadphones(@PathVariable Long headphonesId) {
        deleteHeadphonesUseCase.execute(headphonesId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllHeadphones() {
        deleteAllHeadphonesUseCase.execute();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Headphones> getHeadphones() {
        return getAllHeadphonesUseCase.execute();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Headphones updateHeadphones(@RequestBody EditHeadphonesRequest request) {
        return editHeadphonesUseCase.execute(request.getHeadphonesId(), request.getStatus());
    }
}

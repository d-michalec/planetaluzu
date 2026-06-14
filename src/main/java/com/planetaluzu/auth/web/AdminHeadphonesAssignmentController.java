package com.planetaluzu.auth.web;

import com.planetaluzu.auth.web.dto.AssignHeadphonesRequest;
import com.planetaluzu.auth.web.dto.Attendee;
import com.planetaluzu.auth.web.dto.ManualAssignHeadphonesRequest;
import com.planetaluzu.auth.web.dto.ReturnHeadphonesRequest;
import com.planetaluzu.headphonesAssignment.application.port.AssignHeadphonesToUserUseCase;
import com.planetaluzu.headphonesAssignment.application.port.DeleteAllHeadphonesAssignmentsUseCase;
import com.planetaluzu.headphonesAssignment.application.port.GetHeadphonesAssignmentUseCase;
import com.planetaluzu.headphonesAssignment.application.port.UserReturnHeadphonesUseCase;
import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class AdminHeadphonesAssignmentController {

    private final AssignHeadphonesToUserUseCase assignHeadphonesToUserUseCase;
    private final UserReturnHeadphonesUseCase userReturnHeadphonesUseCase;
    private final GetHeadphonesAssignmentUseCase getHeadphonesAssignmentUseCase;
    private final DeleteAllHeadphonesAssignmentsUseCase deleteAllHeadphonesAssignmentsUseCase;

    @PostMapping("/assign-headphones")
    @ResponseStatus(HttpStatus.OK)
    public HeadphonesAssignment assignHeadphones(@RequestBody AssignHeadphonesRequest request) {
        return assignHeadphonesToUserUseCase.execute(
                request.getTicketUUID(),
                request.getHeadphonesId()
        );
    }

    @PostMapping("/assign-headphones/manual")
    @ResponseStatus(HttpStatus.OK)
    public HeadphonesAssignment assignHeadphonesManually(@RequestBody ManualAssignHeadphonesRequest request) {
        return assignHeadphonesToUserUseCase.executeByEmail(
                request.getEmail(),
                request.getHeadphonesId()
        );
    }

    @PutMapping("/return-headphones")
    @ResponseStatus(HttpStatus.OK)
    public HeadphonesAssignment returnHeadphones(@RequestBody ReturnHeadphonesRequest request) {
        return userReturnHeadphonesUseCase.execute(
                request.getHeadphonesId(),
                request.getStatus()
        );
    }

    @GetMapping("/attendees")
    @ResponseStatus(HttpStatus.OK)
    public List<Attendee> getAttendees() {
        return getHeadphonesAssignmentUseCase.execute();
    }

    @DeleteMapping("/attendees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllAttendees() {
        deleteAllHeadphonesAssignmentsUseCase.execute();
    }
}

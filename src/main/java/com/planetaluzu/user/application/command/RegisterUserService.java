package com.planetaluzu.user.application.command;

import com.planetaluzu.user.application.port.in.RegisterUserUseCase;
import com.planetaluzu.user.application.port.out.EmailSender;
import com.planetaluzu.user.domain.User;
import com.planetaluzu.user.domain.exception.UserAlreadyExistsException;
import com.planetaluzu.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.planetaluzu.user.application.util.ReservationNumberGenerator.generateReservationNumber;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @Override
    public User execute(String firstName, String lastName, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(email);
        }

        String reservationId = generateReservationNumber(firstName, lastName);
        User user = new User(firstName, lastName, email, reservationId);
        User savedUser = userRepository.save(user);

        emailSender.generateReservationEmail(savedUser.getEmail(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getReservationId(), savedUser.getExpiresAt());

        return savedUser;
    }
}
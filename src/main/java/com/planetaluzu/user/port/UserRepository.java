package com.planetaluzu.user.port;

import com.planetaluzu.user.domain.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByReservationId(String reservationId);

//    Optional<User> findById(Long id);
//
//    List<User> findAll();
}
package com.planetaluzu.user.port;

import com.planetaluzu.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

//    Optional<User> findById(Long id);
//
//    List<User> findAll();
}
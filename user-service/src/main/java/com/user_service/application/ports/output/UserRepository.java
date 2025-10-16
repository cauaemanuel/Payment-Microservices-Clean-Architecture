package com.user_service.application.ports.output;

import com.user_service.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);
}

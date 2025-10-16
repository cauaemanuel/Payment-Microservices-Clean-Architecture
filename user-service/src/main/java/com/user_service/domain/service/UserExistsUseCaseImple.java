package com.user_service.domain.service;

import com.user_service.application.ports.output.UserRepository;
import com.user_service.application.ports.input.UserExistsUseCase;

public class UserExistsUseCaseImple implements UserExistsUseCase {

    private UserRepository userRepository;

    public UserExistsUseCaseImple(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

package com.user_service.domain.service;

import com.user_service.domain.entity.User;
import com.user_service.domain.entity.UserRole;
import com.user_service.application.ports.output.UserRepository;
import com.user_service.application.ports.input.RegisterUserUseCase;
import com.user_service.application.dto.CreateUserDTO;
import com.user_service.infrastructure.security.SecurityConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RegisterUserUseCaseImple implements RegisterUserUseCase {

    private UserRepository userRepository;
    private SecurityConfig securityConfig;

    public RegisterUserUseCaseImple (UserRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public void execute(CreateUserDTO createUserDto) {
        if (userRepository.findByEmail(createUserDto.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists with this email");
        }

        User newUser = new User();
        newUser.setEmail(createUserDto.email());
        newUser.setSenha(securityConfig.passwordEncoder().encode(createUserDto.password()));
        newUser.setNome(createUserDto.nome());
        newUser.setRole(UserRole.fromString(createUserDto.role()));

        userRepository.save(newUser);
    }
}

package com.user_service.application.ports.input;

import com.user_service.application.dto.CreateUserDTO;


public interface RegisterUserUseCase {

    void execute(CreateUserDTO createUserDto);
}

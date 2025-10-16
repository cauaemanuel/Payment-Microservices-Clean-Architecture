package com.user_service.application.ports.input;

import com.user_service.application.dto.LoginUserDto;
import com.user_service.application.dto.RecoveryJwtTokenDto;

public interface LoginUserUseCase {

    RecoveryJwtTokenDto execute(LoginUserDto loginUserDto);

}

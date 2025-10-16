package com.user_service.domain.service;

import com.user_service.application.ports.input.LoginUserUseCase;
import com.user_service.application.dto.LoginUserDto;
import com.user_service.application.dto.RecoveryJwtTokenDto;
import com.user_service.adapters.output.persistence.UserEntity;
import com.user_service.infrastructure.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginUserUseCaseImple implements LoginUserUseCase {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public LoginUserUseCaseImple(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public RecoveryJwtTokenDto execute(LoginUserDto loginUserDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserEntity userDetails = (UserEntity) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(tokenService.generateToken(userDetails));
    }
}

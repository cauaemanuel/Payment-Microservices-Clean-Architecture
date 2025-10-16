package com.user_service.infrastructure.config;

import com.user_service.application.ports.input.GetEmailByTokenUseCase;
import com.user_service.domain.service.GetEmailByTokenUseCaseImple;
import com.user_service.adapters.output.persistence.UserRepositoryImple;
import com.user_service.application.ports.input.LoginUserUseCase;
import com.user_service.application.ports.input.RegisterUserUseCase;
import com.user_service.application.ports.input.UserExistsUseCase;
import com.user_service.domain.service.LoginUserUseCaseImple;
import com.user_service.domain.service.RegisterUserUseCaseImple;
import com.user_service.domain.service.UserExistsUseCaseImple;
import com.user_service.application.ports.output.UserRepository;
import com.user_service.adapters.output.persistence.SpringDataUserRepository;
import com.user_service.infrastructure.security.SecurityConfig;
import com.user_service.infrastructure.security.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class BeanConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, SecurityConfig securityConfig) {
        return new RegisterUserUseCaseImple(userRepository, securityConfig);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(AuthenticationManager authenticationManager, TokenService tokenService) {
        return new LoginUserUseCaseImple(authenticationManager, tokenService);
    }

    @Bean
    public UserExistsUseCase userExistsUseCase(UserRepository userRepository) {
        return new UserExistsUseCaseImple(userRepository);
    }

    @Bean
    public UserRepository userRepository(SpringDataUserRepository springDataUserRepository) {
        return new UserRepositoryImple(springDataUserRepository);
    }

    @Bean
    public GetEmailByTokenUseCase getEmailByTokenUseCase(TokenService tokenService) {
        return new GetEmailByTokenUseCaseImple(tokenService);
    }

}
package com.user_service.infrastructure.security;

import com.user_service.adapters.output.persistence.SpringDataUserRepository;
import com.user_service.adapters.output.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImple implements UserDetailsService {

    @Autowired
    private SpringDataUserRepository springDataUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = springDataUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));
        return user;
    }

}

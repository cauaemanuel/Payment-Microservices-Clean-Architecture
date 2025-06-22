package com.user_service.adapter.repository;

import com.user_service.domain.entity.User;
import com.user_service.domain.repository.UserRepository;
import com.user_service.infrastructure.persistence.SpringDataUserRepository;
import com.user_service.infrastructure.persistence.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserAdapterRepository implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    public UserAdapterRepository(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity savedEntity = springDataUserRepository.save(entity);
        return toDomain(savedEntity);
    }

    public Optional<User> findByEmail(String email) {
        var userEntity = springDataUserRepository.findByEmail(email);
        return userEntity.map(this::toDomain);
    }

    public Optional<User> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return springDataUserRepository.findById(id).map(this::toDomain);
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setNome(user.getNome());
        entity.setSenha(user.getSenha());
        entity.setRole(user.getRole());
        entity.setEmail(user.getEmail());
        return entity;
    }

    private User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setNome(entity.getNome());
        user.setEmail(entity.getEmail());
        user.setSenha(entity.getSenha());
        user.setRole(user.getRole());
        return user;
    }
}

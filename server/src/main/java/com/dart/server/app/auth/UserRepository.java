package com.dart.server.app.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByUsername(String username);

    java.util.Optional<UserEntity> findByUsername(String username);
}
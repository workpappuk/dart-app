package com.dart.server.app.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    java.util.Optional<UserEntity> findByUsername(String username);
}
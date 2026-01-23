package com.dart.server.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public void assignRole(Long userId, Long roleId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        RoleEntity role = roleService.findById(roleId).orElseThrow();
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void removeRole(Long userId, Long roleId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        RoleEntity role = roleService.findById(roleId).orElseThrow();
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
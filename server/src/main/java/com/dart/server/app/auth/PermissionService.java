package com.dart.server.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public List<PermissionEntity> findAll() {
        return permissionRepository.findAll();
    }

    public Optional<PermissionEntity> findById(Long id) {
        return permissionRepository.findById(id);
    }

    public PermissionEntity save(PermissionEntity permission) {
        return permissionRepository.save(permission);
    }

    public void deleteById(Long id) {
        permissionRepository.deleteById(id);
    }
}
package com.dart.server.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleRepository roleRepository;

    public void assignPermission(UUID roleId, UUID permissionId) {
        RoleEntity role = roleRepository.findById(roleId).orElseThrow();
        PermissionEntity permission = permissionService.findById(permissionId).orElseThrow();
        if (role.getPermissions() == null) {
            role.setPermissions(new java.util.HashSet<>());
        }
        role.getPermissions().add(permission);
        roleRepository.save(role);
    }

    public void removePermission(UUID roleId, UUID permissionId) {
        RoleEntity role = roleRepository.findById(roleId).orElseThrow();
        PermissionEntity permission = permissionService.findById(permissionId).orElseThrow();
        if (role.getPermissions() != null) {
            role.getPermissions().remove(permission);
            roleRepository.save(role);
        }
    }

    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public Optional<RoleEntity> findById(UUID id) {
        return roleRepository.findById(id);
    }

    public RoleEntity save(RoleEntity role) {
        return roleRepository.save(role);
    }

    public void deleteById(UUID id) {
        roleRepository.deleteById(id);
    }

}
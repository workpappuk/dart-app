package com.dart.server.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleRepository roleRepository;

    public void assignPermission(Long roleId, Long permissionId) {
        RoleEntity role = roleRepository.findById(roleId).orElseThrow();
        PermissionEntity permission = permissionService.findById(permissionId).orElseThrow();
        if (role.getPermissions() == null) {
            role.setPermissions(new java.util.HashSet<>());
        }
        role.getPermissions().add(permission);
        roleRepository.save(role);
    }

    public void removePermission(Long roleId, Long permissionId) {
        RoleEntity role = roleRepository.findById(roleId).orElseThrow();
        PermissionEntity permission = permissionService.findById(permissionId).orElseThrow();
        if (role.getPermissions() != null) {
            role.getPermissions().remove(permission);
            roleRepository.save(role);
        }
    }

    // ...existing code...

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    }

    public RoleEntity save(RoleEntity role) {
        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}
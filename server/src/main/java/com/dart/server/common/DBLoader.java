package com.dart.server.common;

import com.dart.server.app.auth.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DBLoader {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private static EnumMap<ERole, EPermission[]> getERoleEnumMap() {
        EnumMap<ERole, EPermission[]> rolePermissions = new EnumMap<>(ERole.class);
        rolePermissions.put(ERole.ADMIN, new EPermission[]{
                EPermission.USER_CREATE,
                EPermission.USER_READ,
                EPermission.USER_UPDATE,
                EPermission.USER_DELETE,
                EPermission.ROLE_CREATE,
                EPermission.ROLE_READ,
                EPermission.ROLE_UPDATE,
                EPermission.ROLE_DELETE
        });
        rolePermissions.put(ERole.USER, new EPermission[]{
                EPermission.USER_CREATE,
                EPermission.USER_READ,
                EPermission.USER_UPDATE,
                EPermission.USER_DELETE,
        });
        return rolePermissions;
    }

    private String keyGenerator() {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        return base64Key;
    }

    public void warmDB() {

        EnumMap<ERole, EPermission[]> rolePermissions = getERoleEnumMap();

        rolePermissions.values()
                .stream()
                .flatMap(Arrays::stream)
                .distinct()
                .filter(ePermission -> permissionService.findByName(ePermission.name()) == null)
                .map(ePermission -> {
                    PermissionEntity permission = new PermissionEntity();
                    permission.setName(ePermission.name());
                    return permissionService.save(
                            permission
                    );
                })
                .forEach(permission -> log.info("Inserted permission: {}", permission));


        rolePermissions.entrySet()
                .stream()
                .filter(entry -> roleService.findByName(entry.getKey().name()) == null)
                .forEach(entry -> {
                    RoleEntity role = new RoleEntity();
                    role.setName(entry.getKey().name());
                    Set<PermissionEntity> permissionEntitySet = Arrays.stream(entry.getValue())
                            .map(ePermission -> permissionService.findByName(ePermission.name()))
                            .filter(java.util.Objects::nonNull)
                            .collect(Collectors.toSet());
                    role.setPermissions(permissionEntitySet);
                    RoleEntity savedRole = roleService.save(role);
                    // Ensure role-permission join table is populated
                    savedRole.setPermissions(permissionEntitySet);
                    roleService.save(savedRole);
                    log.info("Inserted role: {} with permissions: {}", savedRole.getName(), permissionEntitySet.stream().map(PermissionEntity::getName).collect(Collectors.toList()));
                });


        Arrays.asList("testAdmin-testAdminPassword-ADMIN", "testUser-testUserPassword-USER").forEach(userDef -> {
            String[] parts = userDef.split("-");
            String username = parts[0];
            String password = parts[1];
            String roleName = parts[2];

            if (!userService.existsByUsername(username)) {
                UserEntity user = new UserEntity();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                UserEntity userEntity = userService.save(user);

                // Assign role to user
                RoleEntity roleEntity = roleService.findByName(roleName);
                userEntity.setRoles(Set.of(roleEntity));
                userService.save(userEntity);

                log.info("Inserted default user: {} with password: {} and role: {}", username, password, roleName);
            }
        });


    }

}

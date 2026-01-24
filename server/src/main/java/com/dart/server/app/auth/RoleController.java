package com.dart.server.app.auth;

import com.dart.server.common.response.DartApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role", description = "Role management APIs")
public class RoleController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @Operation(summary = "Assign permission to role", description = "Assigns a permission to a role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission assigned to role successfully"),
            @ApiResponse(responseCode = "404", description = "Role or permission not found")
    })
    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Void> assignPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        if (!roleService.findById(roleId).isPresent() || !permissionService.findById(permissionId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        roleService.assignPermission(roleId, permissionId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove permission from role", description = "Removes a permission from a role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission removed from role successfully"),
            @ApiResponse(responseCode = "404", description = "Role or permission not found")
    })
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Void> removePermissionFromRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        if (!roleService.findById(roleId).isPresent() || !permissionService.findById(permissionId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        roleService.removePermission(roleId, permissionId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all roles", description = "Returns a list of all roles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of roles returned successfully")
    })
    @GetMapping
    public DartApiResponse<List<RoleEntity>> getAllRoles() {
        List<RoleEntity> roles = roleService.findAll();
        return DartApiResponse.<List<RoleEntity>>builder()
                .success(true)
                .message("Roles fetched successfully")
                .data(roles)
                .build();
    }

    @Operation(summary = "Get role by ID", description = "Returns a single role by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found and returned"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/{id}")
    public DartApiResponse<RoleEntity> getRoleById(@Parameter(description = "ID of the role") @PathVariable Long id) {
        return roleService.findById(id)
                .map(role -> DartApiResponse.<RoleEntity>builder()
                        .success(true)
                        .message("Role found")
                        .data(role)
                        .build())
                .orElseGet(() -> DartApiResponse.<RoleEntity>builder()
                        .success(false)
                        .message("Role not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Create a new role", description = "Creates a new role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role created successfully")
    })
    @PostMapping
    public DartApiResponse<RoleEntity> createRole(@Parameter(description = "Role request body") @RequestBody RoleEntity role) {
        RoleEntity saved = roleService.save(role);
        return DartApiResponse.<RoleEntity>builder()
                .success(true)
                .message("Role created successfully")
                .data(saved)
                .build();
    }

    @Operation(summary = "Update a role", description = "Updates an existing role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @PutMapping("/{id}")
    public DartApiResponse<RoleEntity> updateRole(
            @Parameter(description = "ID of the role") @PathVariable Long id,
            @Parameter(description = "Updated role request body") @RequestBody RoleEntity role) {
        role.setId(id);
        return roleService.findById(id)
                .map(existing -> DartApiResponse.<RoleEntity>builder()
                        .success(true)
                        .message("Role updated successfully")
                        .data(roleService.save(role))
                        .build())
                .orElseGet(() -> DartApiResponse.<RoleEntity>builder()
                        .success(false)
                        .message("Role not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Delete a role", description = "Deletes a role by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @DeleteMapping("/{id}")
    public DartApiResponse<Void> deleteRole(@Parameter(description = "ID of the role") @PathVariable Long id) {
        if (!roleService.findById(id).isPresent()) {
            return DartApiResponse.<Void>builder()
                    .success(false)
                    .message("Role not found")
                    .data(null)
                    .build();
        }
        roleService.deleteById(id);
        return DartApiResponse.<Void>builder()
                .success(true)
                .message("Role deleted successfully")
                .data(null)
                .build();
    }
}
package com.dart.server.app.auth;

import com.dart.server.common.response.DartApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
@Tag(name = "Permission", description = "Permission management APIs")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Operation(summary = "Get all permissions", description = "Returns a list of all permissions.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of permissions returned successfully")
    })
    @GetMapping
    public DartApiResponse<List<PermissionEntity>> getAllPermissions() {
        List<PermissionEntity> permissions = permissionService.findAll();
        return DartApiResponse.<List<PermissionEntity>>builder()
                .success(true)
                .message("Permissions fetched successfully")
                .data(permissions)
                .build();
    }

    @Operation(summary = "Get permission by ID", description = "Returns a single permission by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission found and returned"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @GetMapping("/{id}")
    public DartApiResponse<PermissionEntity> getPermissionById(@Parameter(description = "ID of the permission") @PathVariable UUID id) {
        return permissionService.findById(id)
                .map(permission -> DartApiResponse.<PermissionEntity>builder()
                        .success(true)
                        .message("Permission found")
                        .data(permission)
                        .build())
                .orElseGet(() -> DartApiResponse.<PermissionEntity>builder()
                        .success(false)
                        .message("Permission not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Create a new permission", description = "Creates a new permission.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission created successfully")
    })
    @PostMapping
    public DartApiResponse<PermissionEntity> createPermission(@Parameter(description = "Permission request body") @RequestBody PermissionEntity permission) {
        PermissionEntity saved = permissionService.save(permission);
        return DartApiResponse.<PermissionEntity>builder()
                .success(true)
                .message("Permission created successfully")
                .data(saved)
                .build();
    }

    @Operation(summary = "Update a permission", description = "Updates an existing permission.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @PutMapping("/{id}")
    public DartApiResponse<PermissionEntity> updatePermission(
            @Parameter(description = "ID of the permission") @PathVariable UUID id,
            @Parameter(description = "Updated permission request body") @RequestBody PermissionEntity permission) {
        permission.setId(id);
        return permissionService.findById(id)
                .map(existing -> DartApiResponse.<PermissionEntity>builder()
                        .success(true)
                        .message("Permission updated successfully")
                        .data(permissionService.save(permission))
                        .build())
                .orElseGet(() -> DartApiResponse.<PermissionEntity>builder()
                        .success(false)
                        .message("Permission not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Delete a permission", description = "Deletes a permission by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permission deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @DeleteMapping("/{id}")
    public DartApiResponse<Void> deletePermission(@Parameter(description = "ID of the permission") @PathVariable UUID id) {
        if (!permissionService.findById(id).isPresent()) {
            return DartApiResponse.<Void>builder()
                    .success(false)
                    .message("Permission not found")
                    .data(null)
                    .build();
        }
        permissionService.deleteById(id);
        return DartApiResponse.<Void>builder()
                .success(true)
                .message("Permission deleted successfully")
                .data(null)
                .build();
    }
}
package com.dart.server.app.auth;

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
    public List<PermissionEntity> getAllPermissions() {
        return permissionService.findAll();
    }

    @Operation(summary = "Get permission by ID", description = "Returns a single permission by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission found and returned"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PermissionEntity> getPermissionById(@Parameter(description = "ID of the permission") @PathVariable Long id) {
        return permissionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new permission", description = "Creates a new permission.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission created successfully")
    })
    @PostMapping
    public PermissionEntity createPermission(@Parameter(description = "Permission request body") @RequestBody PermissionEntity permission) {
        return permissionService.save(permission);
    }

    @Operation(summary = "Update a permission", description = "Updates an existing permission.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PermissionEntity> updatePermission(
            @Parameter(description = "ID of the permission") @PathVariable Long id,
            @Parameter(description = "Updated permission request body") @RequestBody PermissionEntity permission) {
        permission.setId(id);
        return permissionService.findById(id)
                .map(existing -> ResponseEntity.ok(permissionService.save(permission)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a permission", description = "Deletes a permission by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permission deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@Parameter(description = "ID of the permission") @PathVariable Long id) {
        if (!permissionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        permissionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
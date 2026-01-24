package com.dart.server.app.auth;

import com.dart.server.app.auth.dto.UserMapper;
import com.dart.server.app.auth.dto.UserRequest;
import com.dart.server.app.auth.dto.UserResponse;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public class UserController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Operation(summary = "Assign role to user", description = "Assigns a role to a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role assigned to user successfully"),
            @ApiResponse(responseCode = "404", description = "User or role not found")
    })
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        if (!userService.findById(userId).isPresent() || !roleService.findById(roleId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.assignRole(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove role from user", description = "Removes a role from a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role removed from user successfully"),
            @ApiResponse(responseCode = "404", description = "User or role not found")
    })
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        if (!userService.findById(userId).isPresent() || !roleService.findById(roleId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.removeRole(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users returned successfully")
    })
    @GetMapping
    public DartApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
        return DartApiResponse.<List<UserResponse>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(users)
                .build();
    }

    @Operation(summary = "Get user by ID", description = "Returns a single user by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found and returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public DartApiResponse<UserResponse> getUserById(@Parameter(description = "ID of the user") @PathVariable Long id) {
        return userService.findById(id)
                .map(UserMapper::toResponse)
                .map(user -> DartApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User found")
                        .data(user)
                        .build())
                .orElseGet(() -> DartApiResponse.<UserResponse>builder()
                        .success(false)
                        .message("User not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Create a new user", description = "Creates a new user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully")
    })
    @PostMapping
    public DartApiResponse<UserResponse> createUser(@Parameter(description = "User request body") @RequestBody UserRequest userRequest) {
        UserEntity user = UserMapper.toEntity(userRequest);
        UserResponse response = UserMapper.toResponse(userService.save(user));
        return DartApiResponse.<UserResponse>builder()
                .success(true)
                .message("User created successfully")
                .data(response)
                .build();
    }

    @Operation(summary = "Update a user", description = "Updates an existing user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public DartApiResponse<UserResponse> updateUser(
            @Parameter(description = "ID of the user") @PathVariable Long id,
            @Parameter(description = "Updated user request body") @RequestBody UserRequest userRequest) {
        UserEntity user = UserMapper.toEntity(userRequest);
        user.setId(id);
        return userService.findById(id)
                .map(existing -> DartApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User updated successfully")
                        .data(UserMapper.toResponse(userService.save(user)))
                        .build())
                .orElseGet(() -> DartApiResponse.<UserResponse>builder()
                        .success(false)
                        .message("User not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user") @PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
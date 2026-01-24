package com.dart.server.app.auth;

import com.dart.server.app.auth.dto.UserRequest;
import com.dart.server.app.auth.dto.UserResponse;
import com.dart.server.common.response.DartApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Authentication", description = "Authentication and user registration APIs")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Debug Authentication", description = "Prints the current user's authorities to the console.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authorities printed to console")
    })
    @GetMapping("/debug-auth")
    public void debugAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));
    }

    @Operation(summary = "Register a new user", description = "Registers a new user and assigns the default role.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Username already exists")
    })
    @PostMapping("/register")
    public DartApiResponse<UserResponse> register(@RequestBody UserRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return DartApiResponse.<UserResponse>builder()
                    .success(false)
                    .message("Username already exists")
                    .data(null)
                    .build();
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Assign default role 'user'
        RoleEntity userRole = roleService.findByName(ERole.USER.name());
        user.setRoles(Collections.singleton(userRole));
        userService.save(user);
        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername());
        return DartApiResponse.<UserResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(userResponse)
                .build();
    }

    @Operation(summary = "Login user", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public DartApiResponse<Map<String, Object>> login(@RequestBody UserRequest request) {
        UserEntity user = userService.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return DartApiResponse.<Map<String, Object>>builder()
                    .success(false)
                    .message("Invalid credentials")
                    .data(null)
                    .build();
        }
        String token = jwtTokenProvider.createToken(user.getId(), user.getRoles());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        return DartApiResponse.<Map<String, Object>>builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build();
    }
}
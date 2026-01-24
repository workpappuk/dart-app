package com.dart.server.app.auth;

import com.dart.server.app.auth.dto.UserRequest;
import com.dart.server.app.auth.dto.UserResponse;
import com.dart.server.common.utils.DartApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
        RoleEntity userRole = roleService.findByName("user");
        user.setRoles(Collections.singleton(userRole));
        userService.save(user);
        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername());
        return DartApiResponse.<UserResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(userResponse)
                .build();
    }

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
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        return DartApiResponse.<Map<String, Object>>builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build();
    }
}
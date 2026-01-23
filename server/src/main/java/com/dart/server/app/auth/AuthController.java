package com.dart.server.app.auth;

import com.dart.server.app.auth.dto.UserRequest;
import com.dart.server.app.auth.dto.UserResponse;
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
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Username already exists"));
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Assign default role 'user'
        RoleEntity userRole = roleService.findByName("user");
        user.setRoles(Collections.singleton(userRole));
        userService.save(user);
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        UserEntity user = userService.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Invalid credentials"));
        }
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", new UserResponse(user.getId(), user.getUsername()));
        return ResponseEntity.ok(response);
    }
}
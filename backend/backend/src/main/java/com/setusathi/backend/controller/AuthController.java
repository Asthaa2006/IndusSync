package com.setusathi.backend.controller;

import com.setusathi.backend.dto.LoginRequest;
import com.setusathi.backend.dto.RegisterRequest;
import com.setusathi.backend.model.Role;
import com.setusathi.backend.model.User;
import com.setusathi.backend.repository.UserRepository;
import com.setusathi.backend.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUserId(request.userId())) {
            return ResponseEntity.badRequest().body("User ID already taken!");
        }
        User user = new User();
        user.setUserId(request.userId());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.ROLE_APPLICANT);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/admin/create-official")
    public ResponseEntity<?> createOfficial(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUserId(request.userId())) {
            return ResponseEntity.badRequest().body("User ID already taken!");
        }
        User official = new User();
        official.setUserId(request.userId());
        official.setName(request.name());
        official.setPassword(passwordEncoder.encode(request.password()));
        official.setRole(Role.ROLE_OFFICIAL);
        userRepository.save(official);
        return ResponseEntity.ok("Government Official Account Created Successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userRepository.findByUserId(request.userId())
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword()))
                .map(u -> ResponseEntity.ok(jwtUtils.generateToken(u.getUserId(), u.getRole().name())))
                .orElse(ResponseEntity.status(401).body("Invalid credentials!"));
    }
}
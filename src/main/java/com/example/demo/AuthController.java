package com.example.demo;

import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        logger.info("Registering user: {}", user.getUsername());
        authService.registerUser(user);
        logger.info("User registered successfully: {}", user.getUsername());
        return ResponseEntity.ok("User registered successfully");
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Logging in user: {}", loginRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = authService.findByUsername(loginRequest.getUsername());
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        logger.info("User logged in successfully: {}", loginRequest.getUsername());
        return ResponseEntity.ok(token);
    }
}
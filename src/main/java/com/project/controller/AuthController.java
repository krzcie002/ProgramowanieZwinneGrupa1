package com.project.controller;

import com.project.auth.Credentials;
import com.project.auth.RegisterRequest;
import com.project.auth.Tokens;
import com.project.model.User;
import com.project.service.AuthService;
import com.project.service.ValidationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Auth")
public class AuthController {
    private final AuthService authService;
    private final ValidationService<RegisterRequest> userValidator;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        userValidator.validate(request);
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody Credentials credentials) {
        return ResponseEntity.ok(authService.authenticate(credentials));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Tokens> refreshToken(@RequestBody Tokens tokens) {
        return ResponseEntity.ok(authService.refreshTokens(tokens));
    }
}

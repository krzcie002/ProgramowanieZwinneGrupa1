package com.project.service;

import com.project.auth.Credentials;
import com.project.auth.RegisterRequest;
import com.project.auth.Tokens;
import com.project.interfaces.IUserService;
import com.project.model.Role;
import com.project.interfaces.IAuthService;
import com.project.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.student)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.createUser(user);
    }

    @Override
    public Tokens authenticate(Credentials credentials) {
        return authenticate(credentials.getEmail(), credentials.getPassword());
    }

    private Tokens authenticate(@NonNull String email, @NonNull String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userService
                .getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", email)));
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Tokens refreshTokens(Tokens tokens) {
        final String refreshToken = tokens.getRefreshToken();
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brak tokenu odświeżania");
        }
        final String prefix = "Bearer ";
        final String token = refreshToken.startsWith(prefix) ? refreshToken.substring(prefix.length()) : refreshToken;
        final String email = jwtService.extractUsernameFromRefreshToken(token);
        if(email != null && !email.isBlank()) {
            var user = userService.getUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", email)));
            if(jwtService.isRefreshTokenValid(token, user)) {
                var accessToken = jwtService.generateAccessToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);
                return Tokens.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token odświeżania stracił ważność");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Niepoprawny format tokenu odświeżania");
        }
    }
}

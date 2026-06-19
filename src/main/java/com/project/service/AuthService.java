package com.project.service;

import com.project.auth.Credentials;
import com.project.auth.RegisterRequest;
import com.project.auth.Tokens;
import com.project.auth.RefreshTokenRequest;
import com.project.dto.UserCreateRequest;
import com.project.interfaces.IUserService;
import com.project.interfaces.IAuthService;
import com.project.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IUserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegisterRequest request) {

        UserCreateRequest createRequest = new UserCreateRequest(
                Integer.parseInt(request.getIndex()),
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
        );

        userService.createUser(createRequest);
    }

    @Override
    public Tokens authenticate(Credentials credentials) {
        return authenticate(credentials.getEmail(), credentials.getPassword());
    }

    private Tokens authenticate(@NonNull String email, @NonNull String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = (User) authentication.getPrincipal();

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Tokens refreshTokens(RefreshTokenRequest request) {
        final String refreshToken = request.refreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brak tokenu odświeżania");
        }

        final String prefix = "Bearer ";
        final String token = refreshToken.startsWith(prefix) ? refreshToken.substring(prefix.length()) : refreshToken;

        final String email = jwtService.extractUsernameFromRefreshToken(token);

        if (email == null || email.isBlank()) {
            throw new UsernameNotFoundException(String.format("User %s not found!", email));
        }

        var user = userService.getUserDetailsByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format("User %s not found!", email)
                        ));

        if (jwtService.isRefreshTokenValid(token, user)) {
            var accessToken = jwtService.generateAccessToken(user);
            var newRefreshToken = jwtService.generateRefreshToken(user);

            return Tokens.builder()
                    .accessToken(accessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Token odświeżania stracił ważność"
        );
    }
}

package com.project.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final SecretKey accessSecretKey;
    private final Integer accessTokenValidityInMin;
    private final SecretKey refreshSecretKey;
    private final Integer refreshTokenValidityInMin;

    public JwtService(@Value("${jwt.access-secret-key}") String accessSecretKey,
                      @Value("${jwt.access-token-validity-in-min}") Integer accessTokenValidityInMin,
                      @Value("${jwt.refresh-secret-key}") String refreshSecretKey,
                      @Value("${jwt.refresh-token-validity-in-min}") Integer refreshTokenValidityInMin) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMin = accessTokenValidityInMin;
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_8));
        this.refreshTokenValidityInMin = refreshTokenValidityInMin;
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails,
                                 SecretKey secretKey, Integer tokenValidityInMin) {
        final Instant now = Instant.now();
        final Instant expiration = now.plus(tokenValidityInMin, ChronoUnit.MINUTES);
        final var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("authorities",authorities)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token, SecretKey secretKey) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, SecretKey secretKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private String extractUsername(String token, SecretKey secretKey) {
        return extractClaim(token, secretKey, Claims::getSubject);
    }

    public String extractUsernameFromAccessToken(String token) {
        return extractUsername(token, this.accessSecretKey);
    }

    public String extractUsernameFromRefreshToken(String token) {
        return extractUsername(token, this.refreshSecretKey);
    }

    private Boolean isTokenValid(String token, SecretKey secretKey, UserDetails userDetails) {
        final Claims claims = extractAllClaims(token, secretKey);
        final Date expiration = claims.getExpiration();
        final String username = claims.getSubject();
        boolean valid = !expiration.before(Date.from(Instant.now()));
        return username.equals(userDetails.getUsername()) && valid;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(Map.of(), userDetails);
    }

    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return generateToken(extraClaims, userDetails, this.accessSecretKey, this.accessTokenValidityInMin);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(Map.of(), userDetails);
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return generateToken(extraClaims, userDetails, this.refreshSecretKey, this.refreshTokenValidityInMin);
    }

    public Boolean isAccessTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, this.accessSecretKey, userDetails);
    }

    public Boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, this.refreshSecretKey, userDetails);
    }
}

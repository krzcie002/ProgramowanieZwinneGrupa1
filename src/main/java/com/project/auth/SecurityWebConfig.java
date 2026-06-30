package com.project.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityWebConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                )
                .authorizeHttpRequests(auth -> auth
                        // public static resources
                        .requestMatchers("/css/**",
                                "/",
                                "/login",
                                "/*.html",
                                "/resources/templates/**",
                                "/templates/**",
                                "/js/**",
                                "/favicon.ico").permitAll()

                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/**/*.ico").permitAll()

                        // swagger
                        .requestMatchers(
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // auth endpoints
                        .requestMatchers("/api/login", "/api/register", "/api/refresh").permitAll()

                        // getting details of logged in user
                        .requestMatchers("/api/users/me").authenticated()

                        // protected API
                        .requestMatchers("/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/**").authenticated()

                        // pages
// pages
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/projectList",
                                "/projectList*",              // <--- To wpuści /projectList(wersjaKrzys)
                                "/projectDetails*",           // <--- To wpuści /projectDetails lub /projectDetails.html
                                "/projectAdd",
                                "/projectList(wersjaKrzys)", // <-- NOWE
                                "/projectDetails",
                                "/projectEdit",
                                "/userList"
                        ).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

package com.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE.equals(isActive) && !Boolean.TRUE.equals(isDeleted);
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE.equals(isActive) && !Boolean.TRUE.equals(isDeleted);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE.equals(isActive) && !Boolean.TRUE.equals(isDeleted);
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(isActive) && !Boolean.TRUE.equals(isDeleted);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


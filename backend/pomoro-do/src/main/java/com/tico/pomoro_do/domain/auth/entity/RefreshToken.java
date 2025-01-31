package com.tico.pomoro_do.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "email", nullable = false) // username → email
    private String email;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Builder
    public RefreshToken(String email, String refreshToken, String deviceId, LocalDateTime expiresAt) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.deviceId = deviceId;
        this.expiresAt = expiresAt;
    }
}
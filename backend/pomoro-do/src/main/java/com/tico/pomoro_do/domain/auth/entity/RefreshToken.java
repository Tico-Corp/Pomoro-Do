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

    private String username;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    // 생성자

    @Builder
    public RefreshToken(String username, String refreshToken, String deviceId, LocalDateTime expiresAt) {
        this.username = username;
        this.refreshToken = refreshToken;
        this.deviceId = deviceId;
        this.expiresAt = expiresAt;
    }
}
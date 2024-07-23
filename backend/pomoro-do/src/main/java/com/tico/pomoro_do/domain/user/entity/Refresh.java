package com.tico.pomoro_do.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "refresh")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_id")
    private Long id;

    private String username;
    @Column(name = "refresh_token")
    private String refreshToken;
    private String expiration;

    // 생성자
    @Builder
    public Refresh (String username, String refreshToken, String expiration){
        this.username = username;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
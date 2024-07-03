package com.tico.pomoro_do.domain.user.entity;

import com.tico.pomoro_do.global.common.enums.SocialProvider;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_login")
public class SocialLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_login_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProvider provider;

    @Column(name = "social_id", nullable = false)
    private String socialId;
}

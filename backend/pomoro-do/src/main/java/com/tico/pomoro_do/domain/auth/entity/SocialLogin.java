package com.tico.pomoro_do.domain.auth.entity;

import com.tico.pomoro_do.domain.auth.enums.OAuthProvider;
import com.tico.pomoro_do.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_login")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private OAuthProvider provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;    // 소셜 로그인 제공자의 ID

    @Builder
    public SocialLogin(User user, OAuthProvider provider, String providerId) {
        this.user = user;
        this.provider = provider;
        this.providerId = providerId;
    }
}

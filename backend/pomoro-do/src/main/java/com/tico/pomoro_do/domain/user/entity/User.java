package com.tico.pomoro_do.domain.user.entity;

import com.tico.pomoro_do.global.common.entity.BaseTimeEntity;
import com.tico.pomoro_do.domain.user.enums.UserRole;
import com.tico.pomoro_do.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 소셜 로그인 제공자의 이메일

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // 활성화 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Builder
    public User(String email, String nickname, String profileImageUrl, UserRole role) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
//
//    public void updateRole(UserRole role) {
//        this.role = role;
//    }

//    public void updateStatus(UserStatus status) {
//        this.status = status;
//    }

    public void deactivate() {
        this.status = UserStatus.DEACTIVATED;
    }
}
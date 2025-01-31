package com.tico.pomoro_do.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 인증(Authentication) 과정에서 사용자 정보를 담는 내부 객체.
 * JWT에서 추출된 사용자 정보 (email, role)를 포함하며,
 * CustomUserDetails에서 사용됩니다.
 */
@Getter
public class AuthUser {
    private String email;
    private String role;

    @Builder
    public AuthUser(String email, String role){
        this.email = email;
        this.role = role;
    }
}
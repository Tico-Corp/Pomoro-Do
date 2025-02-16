package com.tico.pomoro_do.domain.auth.security;

import com.tico.pomoro_do.domain.auth.dto.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Spring Security의 `UserDetails` 구현체로, 사용자 인증 정보를 관리하는 클래스.
 * - `AuthUser`를 기반으로 사용자 정보를 제공하며, `username` 필드는 `email`을 사용.
 * - 인증된 사용자 정보를 Spring Security에서 활용할 수 있도록 지원.
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final AuthUser authUser;

    // Spring Security가 내부적으로 사용하는 사용자 식별자
    @Override
    public String getUsername() {
//        return String.valueOf(authUser.getUserId());
        return authUser.getEmail();     // username은 email로 대체
    }

    // 실제 비즈니스 로직에서 사용할 userId 반환
    public Long getUserId() {
        return authUser.getUserId();
    }

    // 사용자 권한 반환 -> role 가져오기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                // Spring Security의 권한 규칙에 맞추기 위해 "ROLE_" 접두사를 추가
                // return "ROLE_" + user.getRole();
                return authUser.getRole();
            }
        });

        return collection;
    }

    // 소셜 로그인이므로 패스워드 사용하지 않음
    @Override
    public String getPassword() {

//        return account.getPassword();
        return null;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    // 자격 증명 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {

        return true;
    }
}
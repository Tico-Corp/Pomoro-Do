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

    // 비밀번호는 JWT 인증 방식에서는 사용되지 않으므로 null 반환
    @Override
    public String getPassword() {

//        return account.getPassword();
        return null;
    }

    // 사용자 고유 식별자(username)로 email을 반환
    @Override
    public String getUsername() {

        return authUser.getEmail();     // username은 email로 대체됨
    }

    // 계정이 만료되지 않았는지 확인 (항상 true)
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    // 계정이 잠겨있지 않은지 확인 (항상 true)
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    // 비밀번호가 만료되지 않았는지 확인 (항상 true)
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    // 계정이 활성화되어 있는지 확인 (항상 true)
    @Override
    public boolean isEnabled() {

        return true;
    }
}
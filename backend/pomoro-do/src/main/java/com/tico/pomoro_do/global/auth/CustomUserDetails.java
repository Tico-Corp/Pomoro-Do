package com.tico.pomoro_do.global.auth;

import com.tico.pomoro_do.domain.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//DTO
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserDTO userDTO;

    //role 가져오기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                // Spring Security의 권한 규칙에 맞추기 위해 "ROLE_" 접두사를 추가
                // return "ROLE_" + user.getRole();
                return userDTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

//        return account.getPassword();
        return null;
    }

    @Override
    public String getUsername() {

        return userDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
package com.tico.pomoro_do.global.auth;

import com.tico.pomoro_do.domain.user.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//DTO
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Account account;

    //role 가져오기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return account.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return account.getPassword();
    }

    @Override
    public String getUsername() {

        return account.getUsername();
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
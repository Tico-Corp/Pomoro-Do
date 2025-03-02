package com.tico.pomoro_do.global.security.auth;

import com.tico.pomoro_do.global.security.auth.AuthUser;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //생성자 주입
//    public CustomUserDetailsService(UserRepository userRepository) {
//
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB에서 조회
        Optional<User> userData = userRepository.findByEmail(username);

        // 데이터가 있으면 검증 진행
        if (userData.isPresent()) {
            User user = userData.get();
            AuthUser authUser = AuthUser.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .role(String.valueOf(user.getRole()))
                    .build();

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(authUser);
        }

        return null;
    }
}
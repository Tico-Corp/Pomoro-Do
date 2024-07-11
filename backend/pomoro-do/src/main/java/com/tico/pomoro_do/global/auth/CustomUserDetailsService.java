package com.tico.pomoro_do.global.auth;

import com.tico.pomoro_do.domain.user.dto.UserDTO;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
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

//        //DB에서 조회
//        User userData = userRepository.findByUsername(username);
//
//        //데이터가 있으면 검증 진행
//        if (userData != null) {
//
//            UserDTO userDTO = UserDTO.builder()
//                    .username(userData.getUsername())
//                    .role(String.valueOf(userData.getRole()))
//                    .build();

        // DB에서 조회
        Optional<User> userData = userRepository.findByUsername(username);


        // 데이터가 있으면 검증 진행
        if (userData.isPresent()) {
            User user = userData.get();
            UserDTO userDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .role(String.valueOf(user.getRole()))
                    .build();

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userDTO);
        }

        return null;
    }
}
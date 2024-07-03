package com.tico.pomoro_do.global.auth;

import com.tico.pomoro_do.domain.user.entity.Account;
import com.tico.pomoro_do.domain.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    //생성자 주입
//    public CustomUserDetailsService(AccountRepository accountRepository) {
//
//        this.accountRepository = accountRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB에서 조회
        Account accountData = accountRepository.findByUsername(username);

        //데이터가 있으면 검증 진행
        if (accountData != null) {

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(accountData);
        }

        return null;
    }
}
package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.AccountDTO;
import com.tico.pomoro_do.domain.user.entity.Account;
import com.tico.pomoro_do.domain.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void joinProcess(AccountDTO accountDTO) {

        String username = accountDTO.getUsername();
        String password = accountDTO.getPassword();

        Boolean isExist = accountRepository.existsByUsername(username);

        if (isExist) {

            return;
        }

        Account account = Account.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_ADMIN") //역할은 "접두사_원하는권한"의 형태로 설정
                .build();
        //저장
        accountRepository.save(account);
    }

}

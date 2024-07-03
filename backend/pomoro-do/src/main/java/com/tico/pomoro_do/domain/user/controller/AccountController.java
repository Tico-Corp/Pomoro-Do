package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.AccountDTO;
import com.tico.pomoro_do.domain.user.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody // 응답 형태
@RequiredArgsConstructor // 파이널 필드만 가지고 생성사 주입 함수 만듬 (따로 작성할 필요 없다.)
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/join")
    public String joinProcess(AccountDTO accountDTO) {

        System.out.println(accountDTO.getUsername());
        accountService.joinProcess(accountDTO);

        return "ok";
    }
}
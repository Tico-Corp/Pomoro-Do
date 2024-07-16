package com.tico.pomoro_do.global.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomSuccessCode {

    SUCCESS(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
    //사용자
    GOOGLE_LOGIN_SUCCESS(HttpStatus.OK, "구글 로그인에 성공했습니다."),
    //토큰
    GOOGLE_SIGNUP_SUCCESS(HttpStatus.CREATED, "구글 회원가입에 성공했습니다."),
    ACCESS_TOKEN_REISSUED(HttpStatus.OK,  "액세스 토큰이 성공적으로 재발급되었습니다."),
    //관리자
    ADMIN_LOGIN_SUCCESS(HttpStatus.OK, "관리자 로그인에 성공했습니다."),
    ADMIN_SIGNUP_SUCCESS(HttpStatus.CREATED, "관리자 회원가입에 성공했습니다.");

    private final HttpStatus httpStatus;	// HttpStatus
    private final String message;			// 설명
}

package com.tico.pomoro_do.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    SUCCESS(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),

    //사용자
    GOOGLE_LOGIN_SUCCESS(HttpStatus.OK, "구글 로그인에 성공했습니다."),
    GOOGLE_SIGNUP_SUCCESS(HttpStatus.CREATED, "구글 회원가입에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 및 토큰 삭제에 성공했습니다."),
    USER_FETCH_SUCCESS(HttpStatus.OK, "회원 조회에 성공했습니다."),
    USER_DELETION_SUCCESS(HttpStatus.OK, "회원 탈퇴가 성공적으로 처리되었습니다."),

    //토큰
    ACCESS_TOKEN_REISSUED(HttpStatus.OK,  "액세스 토큰이 성공적으로 재발급되었습니다."),
    ACCESS_TOKEN_VALIDATED(HttpStatus.OK, "액세스 토큰이 유효합니다."),
    REFRESH_TOKEN_VALIDATED(HttpStatus.OK, "리프레시 토큰이 유효합니다."),

    //관리자
    ADMIN_LOGIN_SUCCESS(HttpStatus.OK, "관리자 로그인에 성공했습니다."),
    ADMIN_SIGNUP_SUCCESS(HttpStatus.CREATED, "관리자 회원가입에 성공했습니다."),

    // 이미지
    IMAGE_UPLOAD_SUCCESS(HttpStatus.OK, "이미지가 성공적으로 업로드되었습니다."),
    ;

    private final HttpStatus httpStatus;	// HttpStatus
    private final String message;			// 설명
}

package com.tico.pomoro_do.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    //다양한 상황에서 쓰일 Error Code를 만든다.
    //커스텀 코드는 음수를 사용한다.

    /* 400 BAD_REQUEST : 잘못된 요청 */
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    /* 409 CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */

    //유저 관련 에러: -100번대
    EMAIL_EXIST(HttpStatus.BAD_REQUEST, -100, "이미 사용 중인 이메일입니다."),
    NICKNAME_NULL(HttpStatus.BAD_REQUEST, -101, "닉네임을 입력해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, -102, "가입된 사용자가 아닙니다."),
    PROFILE_UPLOAD_FAILED(HttpStatus.FORBIDDEN, -103, "프로필 이미지 변경에 실패했습니다."),
    USER_ALREADY_REGISTERED(HttpStatus.CONFLICT, -104, "이미 등록된 사용자입니다."),

    //Token 관련 에러 -200번대
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, -201, "액세스 토큰이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, -202, "리프레시 토큰이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN, -203, "액세스 토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN, -204, "리프레시 토큰이 유효하지 않습니다."),
    MISSING_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, -205, "액세스 토큰이 없습니다."),
    MISSING_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, -206, "리프레시 토큰이 없습니다."),
    MISSING_REFRESH_TOKEN_IN_DB(HttpStatus.BAD_REQUEST, -207, "DB에 리프레시 토큰이 없습니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST, -208, "데이터베이스의 리프레시 토큰과 일치하지 않습니다."),
    INVALID_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST, -209, "AUTHORIZATION 헤더의 토큰이 유효하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, -210, "인증되지 않은 접근입니다."),

    //구글 토큰 관련 에러: -300번대
    GOOGLE_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, -300, "구글 ID 토큰 검증에 실패했습니다."),
    GOOGLE_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, -301, "구글 ID 토큰이 유효하지 않습니다."),

    //관리자 관련 에러: -400번대
    NOT_AN_ADMIN(HttpStatus.FORBIDDEN, -400, "관리자 권한이 없습니다."),
    INVALID_ADMIN_EMAIL(HttpStatus.BAD_REQUEST, -401, "허용되지 않은 관리자 이메일입니다."),
    ADMIN_EMAIL_ONLY(HttpStatus.FORBIDDEN, -402, "관리자 이메일만 접근할 수 있습니다."),
    ADMIN_LOGIN_FAILED(HttpStatus.BAD_REQUEST, -403, "관리자 정보가 일치하지 않습니다. 관리자 로그인에 실패했습니다.");

    private final HttpStatus httpStatus;	// HttpStatus
    private final int code;				    // -100
    private final String message;			// 설명

}

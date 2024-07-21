package com.tico.pomoro_do.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * [공통 코드] API 통신에 대한 '에러 코드'를 Enum 형태로 관리를 한다.
 * Global Error CodeList : 전역으로 발생하는 에러코드를 관리한다.
 * Custom Error CodeList : 업무 페이지에서 발생하는 에러코드를 관리한다
 *
 * @author BUGYEONG
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    //다양한 상황에서 쓰일 Error Code를 만든다.

    /**
     * ******************************* Global Error Code List ***************************************
     * HTTP Status Code
     * 400 : Bad Request - 클라이언트의 요청이 잘못된 경우
     * 401 : Unauthorized - 인증이 필요하거나 인증에 실패한 경우
     * 403 : Forbidden - 권한이 없는 경우
     * 404 : Not Found - 요청한 리소스를 찾을 수 없는 경우
     * 500 : Internal Server Error - 서버 내부 오류가 발생한 경우
     * *********************************************************************************************
     */

    // 글로벌 에러 코드: 000번대

    // 400 : Bad Request
    // 클라이언트의 요청이 잘못된 경우
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "G-001", "잘못된 요청입니다."),
    // 요청 헤더가 누락된 경우
    MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "G-002", "요청 헤더가 누락되었습니다."),
    // 요청 본문이 누락된 경우
    MISSING_REQUEST_BODY(HttpStatus.BAD_REQUEST, "G-003", "요청 본문이 누락되었습니다."),
    // 요청 파라미터가 누락된 경우
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "G-004", "요청 파라미터가 누락되었습니다."),
    // 유효성 검증에 실패한 경우
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "G-005", "유효성 검증에 실패했습니다."),
    // 유효하지 않은 값이나 타입이 포함된 경우
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "G-006", "잘못된 타입 값입니다."),
    // 입출력 값이 유효하지 않은 경우
    IO_ERROR(HttpStatus.BAD_REQUEST, "G-007", "입출력 값이 유효하지 않습니다."),
    // JSON 파싱에 실패한 경우
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "G-008", "JSON 파싱 오류가 발생했습니다."),
    // JSON 처리 오류
    JSON_PROCESSING_ERROR(HttpStatus.BAD_REQUEST, "G-009", "JSON 처리 중 오류가 발생했습니다."),
    // 멀티파트 데이터가 유효하지 않은 경우
    INVALID_MULTIPART_DATA(HttpStatus.BAD_REQUEST, "G-010", "잘못된 멀티파트 데이터입니다."),
    // 쿠키에 유효하지 않은 값이 있는 경우
    INVALID_COOKIE(HttpStatus.BAD_REQUEST, "G-011", "쿠키에 유효하지 않은 값이 있습니다."),
    // 헤더에 유효하지 않은 값이 있는 경우
    INVALID_HEADER(HttpStatus.BAD_REQUEST, "G-012", "헤더에 유효하지 않은 값이 있습니다."),

    // 401 : Unauthorized - 인증이 필요하거나 인증에 실패한 경우
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "G-020", "인증되지 않은 사용자입니다."),

    // 403 : Forbidden - 권한이 없는 경우
    FORBIDDEN(HttpStatus.FORBIDDEN, "G-030", "접근 권한이 없습니다."),

    // 404 : Not Found
    // 요청한 리소스를 찾을 수 없는 경우
    NOT_FOUND(HttpStatus.NOT_FOUND, "G-040", "요청한 리소스를 찾을 수 없습니다."),
    // NULL 포인터 예외가 발생한 경우
    NULL_POINTER(HttpStatus.NOT_FOUND, "G-041", "NULL 포인터 예외가 발생했습니다."),

    // 500 : Internal Server Error
    // 서버 내부 오류가 발생한 경우
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-099", "내부 서버 오류가 발생했습니다."),


    /**
     * ******************************* Custom Error CodeList ***************************************
     */

    //유저 관련 에러: -100번대
    EMAIL_EXIST(HttpStatus.BAD_REQUEST, "U-100", "이미 사용 중인 이메일입니다."),
    NICKNAME_NULL(HttpStatus.BAD_REQUEST, "U-101", "닉네임을 입력해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-102", "가입된 사용자가 아닙니다."),
    PROFILE_UPLOAD_FAILED(HttpStatus.FORBIDDEN, "U-103", "프로필 이미지 변경에 실패했습니다."),
    USER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "U-104", "이미 등록된 사용자입니다."),

    //Token 관련 에러 -200번대
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T-201", "액세스 토큰이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T-202", "리프레시 토큰이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN, "T-203", "액세스 토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "T-204", "리프레시 토큰이 유효하지 않습니다."),
    MISSING_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "T-205", "액세스 토큰이 없습니다."),
    MISSING_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "T-206", "리프레시 토큰이 없습니다."),
    MISSING_REFRESH_TOKEN_IN_DB(HttpStatus.BAD_REQUEST, "T-207", "DB에 리프레시 토큰이 없습니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST, "T-208", "데이터베이스의 리프레시 토큰과 일치하지 않습니다."),
    INVALID_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST, "T-209", "AUTHORIZATION 헤더의 토큰이 유효하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "T-210", "인증되지 않은 접근입니다."),
    // JWT 서명 오류
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "T-211", "JWT 서명 검증에 실패했습니다."),
    // 잘못된 JWT 형식
    INVALID_MALFORMED_JWT(HttpStatus.UNAUTHORIZED, "T-212", "잘못된 JWT 토큰입니다."),

    //구글 토큰 관련 에러: -300번대
    GOOGLE_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "G-300", "구글 ID 토큰 검증에 실패했습니다."),
    INVALID_GOOGLE_TOKEN_HEADER(HttpStatus.BAD_REQUEST, "G-301", "GOOGLE_ID_TOKEN 헤더의 토큰이 유효하지 않습니다."),

    //관리자 관련 에러: -400번대
    NOT_AN_ADMIN(HttpStatus.FORBIDDEN, "A-400", "관리자 권한이 없습니다."),
    INVALID_ADMIN_EMAIL(HttpStatus.BAD_REQUEST, "A-401", "허용되지 않은 관리자 이메일입니다."),
    ADMIN_EMAIL_ONLY(HttpStatus.FORBIDDEN, "A-402", "관리자 이메일만 접근할 수 있습니다."),
    ADMIN_LOGIN_FAILED(HttpStatus.BAD_REQUEST, "A-403", "관리자 정보가 일치하지 않습니다. 관리자 로그인에 실패했습니다."),
    ;


    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// U-100
    private final String message;			// 설명

}

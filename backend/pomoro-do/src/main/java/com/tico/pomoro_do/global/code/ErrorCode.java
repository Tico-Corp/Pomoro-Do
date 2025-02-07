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
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "G-001", "클라이언트의 요청이 잘못되었습니다. 요청을 확인하고 다시 시도해 주세요."),
    // 요청 헤더가 누락된 경우
    MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "G-002", "필수 요청 헤더가 누락되었습니다. 요청에 필요한 모든 헤더를 포함해 주세요."),
    // 요청 본문이 누락된 경우
    MISSING_REQUEST_BODY(HttpStatus.BAD_REQUEST, "G-003", "요청 본문이 누락되었습니다. 요청 본문이 포함되어 있는지 확인해 주세요."),
    // 요청 파라미터가 누락된 경우
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "G-004", "필수 요청 파라미터가 누락되었습니다. 요청 파라미터를 확인해 주세요."),
    // 유효성 검증에 실패한 경우
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "G-005", "요청 데이터의 유효성 검증에 실패했습니다. 입력 데이터를 확인해 주세요."),
    // 유효하지 않은 값이나 타입이 포함된 경우
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "G-006", "요청에 포함된 값이 잘못된 타입입니다. 올바른 타입의 값을 제공해 주세요."),
    // 입출력 값이 유효하지 않은 경우
    IO_ERROR(HttpStatus.BAD_REQUEST, "G-007", "입출력 작업 중 오류가 발생했습니다. 서버와의 통신을 확인해 주세요."),
    // JSON 파싱에 실패한 경우
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "G-008", "JSON 파싱 중 오류가 발생했습니다. 요청의 JSON 형식이 올바른지 확인해 주세요."),
    // JSON 처리 오류
    JSON_PROCESSING_ERROR(HttpStatus.BAD_REQUEST, "G-009", "JSON 처리 중 오류가 발생했습니다. JSON 데이터가 유효한지 검토해 주세요."),
    // JSON 매핑 오류
    JSON_MAPPING_ERROR(HttpStatus.BAD_REQUEST, "G-010", "JSON 매핑 오류가 발생했습니다. 요청 JSON과 서버 객체 간의 매핑이 실패했습니다."),
    // 멀티파트 데이터가 유효하지 않은 경우
    INVALID_MULTIPART_DATA(HttpStatus.BAD_REQUEST, "G-011", "멀티파트 데이터가 유효하지 않습니다. 올바른 형식의 멀티파트 데이터를 제공해 주세요."),
    // 쿠키에 유효하지 않은 값이 있는 경우
    INVALID_COOKIE(HttpStatus.BAD_REQUEST, "G-012", "쿠키에 유효하지 않은 값이 포함되어 있습니다. 쿠키 설정을 확인해 주세요."),
    // 헤더에 유효하지 않은 값이 있는 경우
    INVALID_HEADER(HttpStatus.BAD_REQUEST, "G-013", "요청 헤더에 유효하지 않은 값이 포함되어 있습니다. 올바른 헤더 값을 제공해 주세요."),
    // 요청 파라미터의 타입이 예상과 다른 경우
    INVALID_PARAMETER_TYPE(HttpStatus.BAD_REQUEST, "G-014", "요청 파라미터의 타입이 잘못되었습니다."),
    // 업로드 파일의 크기가 허용된 최대 크기를 초과한 경우
    MAX_UPLOAD_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "G-015", "업로드 파일의 크기가 허용된 최대 크기를 초과했습니다. 파일 크기를 줄이거나 다른 파일을 업로드해 주세요."),

    // 401 : Unauthorized - 인증이 필요하거나 인증에 실패한 경우
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "G-020", "인증되지 않은 사용자입니다. 유효한 인증 정보를 제공해 주세요."),

    // 403 : Forbidden - 권한이 없는 경우
    FORBIDDEN(HttpStatus.FORBIDDEN, "G-030", "접근 권한이 없습니다. 필요한 권한을 확인해 주세요."),
    // 접근이 거부된 경우
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "G-031", "접근이 거부되었습니다. 권한이 있는 사용자만 접근할 수 있습니다."),

    // 404 : Not Found
    // 요청한 리소스를 찾을 수 없는 경우
    NOT_FOUND(HttpStatus.NOT_FOUND, "G-040", "요청한 리소스를 찾을 수 없습니다. URL 또는 리소스가 올바른지 확인해 주세요."),
    // 존재하지 않는 엔티티인 경우
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "G-041", "요청한 엔티티를 찾을 수 없습니다. 엔티티 식별자를 확인해 주세요."),
    // 요청한 핸들러를 찾을 수 없는 경우
    NO_HANDLER_FOUND(HttpStatus.NOT_FOUND, "G-042", "요청한 핸들러를 찾을 수 없습니다. 요청 경로가 올바른지 확인해 주세요."),
    // 요청한 리소스를 찾을 수 없는 경우
    NO_RESOURCE_FOUND(HttpStatus.NOT_FOUND, "G-043", "요청한 리소스를 찾을 수 없습니다. 요청 리소스가 존재하는지 확인해 주세요."),
    // 요청한 요소를 찾을 수 없는 경우
    NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, "G-044", "요청한 요소를 찾을 수 없습니다. 요청한 요소가 존재하는지 확인해 주세요."),

    // 405 : Method Not Allowed
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "G-050", "지원하지 않는 HTTP 메서드입니다. 요청한 메서드가 허용되지 않습니다."),

    // 500 : Internal Server Error
    // NULL 포인터 예외가 발생한 경우
    NULL_POINTER(HttpStatus.INTERNAL_SERVER_ERROR, "G-090", "NULL 포인터 예외가 발생했습니다. 서버에서 처리 중 문제가 발생했습니다."),
    // 서버 내부 오류가 발생한 경우
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-099", "내부 서버 오류가 발생했습니다. 서버 관리자에게 문의해 주세요."),

    /**
     * ******************************* Custom Error CodeList ***************************************
     */

    //유저 관련 에러: -100번대
    EMAIL_EXIST(HttpStatus.BAD_REQUEST, "U-100", "이미 사용 중인 이메일입니다."),
    NICKNAME_INVALID(HttpStatus.BAD_REQUEST, "U-101", "닉네임이 비어있거나 유효하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-102", "가입된 사용자가 아닙니다."),
    PROFILE_UPLOAD_FAILED(HttpStatus.FORBIDDEN, "U-103", "프로필 이미지 변경에 실패했습니다."),
    USER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "U-104", "이미 등록된 사용자입니다."),
    NICKNAME_TOO_LONG(HttpStatus.BAD_REQUEST, "U-105", "닉네임이 너무 깁니다. 최대 길이는 10자입니다."),
    USERNAME_MISMATCH(HttpStatus.BAD_REQUEST, "U-106", "현재 유저의 정보와 일치하지 않습니다."),
    USER_ID_INVALID(HttpStatus.BAD_REQUEST, "U-107", "USER ID가 비어있거나 유효하지 않습니다."),
    USER_ID_MISMATCH(HttpStatus.BAD_REQUEST, "U-108", "현재 유저의 정보와 일치하지 않습니다."),


    //follow 관련 에러: -130번대
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "U-130", "본인은 팔로우할 수 없습니다."),
    ALREADY_FOLLOWED(HttpStatus.CONFLICT, "U-131", "이미 팔로우 중입니다."), // 중복 팔로우 에러 코드
    NOT_FOLLOWING(HttpStatus.CONFLICT, "U-132", "팔로우하고 있지 않습니다."),

    //Token 관련 에러 -200번대
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T-201", "액세스 토큰이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T-202", "리프레시 토큰이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN, "T-203", "액세스 토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "T-204", "리프레시 토큰이 유효하지 않습니다."),
    MISSING_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "T-205", "액세스 토큰이 제공되지 않았습니다."),
    MISSING_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "T-206", "리프레시 토큰이 제공되지 않았습니다."),
    INVALID_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST, "T-207", "Authorization 헤더의 토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN_HEADER(HttpStatus.BAD_REQUEST, "T-208", "Refresh-Token 헤더의 토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST, "T-209", "제공된 리프레시 토큰과 서버의 토큰이 일치하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "T-210", "제공된 리프레시 토큰이 서버에 존재하지 않습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "T-211", "제공된 토큰의 유형이 올바르지 않습니다. 올바른 토큰을 사용해 주세요."),

    // JWT 검증 관련 에러 - 230번대
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "T-231", "JWT 서명 검증에 실패했습니다."),
    INVALID_MALFORMED_JWT(HttpStatus.UNAUTHORIZED, "T-232", "잘못된 형식의 JWT입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "T-233", "지원하지 않는 JWT입니다."),
    ILLEGAL_ARGUMENT(HttpStatus.UNAUTHORIZED, "T-234", "잘못된 JWT 토큰입니다."),

    // 구글 토큰 관련 에러 - 290번대
    GOOGLE_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "T-290", "구글 ID 토큰 검증에 실패했습니다."),
    INVALID_GOOGLE_TOKEN_HEADER(HttpStatus.BAD_REQUEST, "T-291", "Google ID Token 헤더가 유효하지 않습니다."),

    // 기기 관련 에러 - 300번대
    DEVICE_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "D-300", "제공된 Device ID가 서버에 존재하지 않습니다."),
    INVALID_DEVICE_ID_HEADER(HttpStatus.BAD_REQUEST, "D-301", "Device ID 헤더의 값이 유효하지 않습니다."),
    DEVICE_ID_MISMATCH(HttpStatus.BAD_REQUEST, "D-302", "제공된 Device ID와 서버의 Device ID가 일치하지 않습니다."),
    DEVICE_ID_INVALID(HttpStatus.BAD_REQUEST, "D-303", "DEVICE ID가 비어있거나 유효하지 않습니다"),

    // 관리자 관련 에러: -400번대
    NOT_AN_ADMIN(HttpStatus.FORBIDDEN, "A-400", "관리자 권한이 없습니다."),
    INVALID_ADMIN_EMAIL(HttpStatus.BAD_REQUEST, "A-401", "허용되지 않은 관리자 이메일입니다."),
    ADMIN_EMAIL_ONLY(HttpStatus.FORBIDDEN, "A-402", "관리자 이메일만 접근할 수 있습니다."),
    ADMIN_LOGIN_FAILED(HttpStatus.BAD_REQUEST, "A-403", "관리자 정보가 일치하지 않습니다. 관리자 로그인에 실패했습니다."),

    // 파일 업로드 관련 에러: -500번대
    FILE_MISSING(HttpStatus.BAD_REQUEST, "F-500", "파일이 요청에 포함되어 있지 않습니다."),
    FILE_NAME_NULL(HttpStatus.BAD_REQUEST, "F-501", "파일의 원래 이름을 가져올 수 없습니다."),
    FILE_CONVERSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F-502", "MultipartFile을 File로 변환하는 중 오류가 발생했습니다."),
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F-503", "S3에 파일 업로드 중 오류가 발생했습니다."),
    LOCAL_FILE_DELETION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F-504", "로컬 파일 삭제에 실패했습니다."),

    // AWS 관련 에러: -510번대
    AWS_ACCESS_DENIED(HttpStatus.FORBIDDEN, "F-510", "AWS 접근이 거부되었습니다."),
    AWS_INVALID_BUCKET(HttpStatus.NOT_FOUND, "F-511", "AWS 버킷이 유효하지 않습니다."),
    AWS_FILE_TOO_LARGE(HttpStatus.BAD_REQUEST, "F-512", "파일이 너무 큽니다."),
    AWS_ACL_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "F-513", "AWS ACL이 지원되지 않습니다."),

    // 카테고리 관련 에러: -600번대
    CATEGORY_TITLE_MISSING(HttpStatus.BAD_REQUEST, "C-600", "카테고리 제목이 누락되었습니다."),
    CATEGORY_VISIBILITY_INVALID(HttpStatus.BAD_REQUEST, "C-601", "카테고리 공개 설정이 유효하지 않습니다."),
    CATEGORY_COLOR_MISSING(HttpStatus.BAD_REQUEST, "C-602", "카테고리 색상이 누락되었습니다."),
    CATEGORY_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C-603", "카테고리 생성 중 오류가 발생했습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "C-604", "해당 카테고리를 찾을 수 없습니다."),
    CATEGORY_DELETION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C-605", "카테고리 삭제 중 오류가 발생했습니다."),
    CATEGORY_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C-606", "카테고리 업데이트 중 오류가 발생했습니다."),
    CATEGORY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "C-607", "그룹 카테고리의 멤버를 찾을 수 없습니다."),
    CATEGORY_MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "C-608", "해당 멤버는 이미 그룹에 포함되어 있습니다."),
    CATEGORY_MEMBER_ADDITION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C-609", "그룹 카테고리에 멤버 추가 중 오류가 발생했습니다."),
    CATEGORY_MEMBER_REMOVAL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C-610", "그룹 카테고리에서 멤버 삭제 중 오류가 발생했습니다."),
    CATEGORY_MEMBER_NOT_FOLLOWED(HttpStatus.BAD_REQUEST, "C-611", "해당 멤버는 호스트가 팔로우한 사용자가 아닙니다."),

    ;


    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// U-100
    private final String message;			// 설명

}

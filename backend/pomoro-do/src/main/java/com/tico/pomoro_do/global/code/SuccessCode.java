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
    FOLLOW_SUCCESS(HttpStatus.CREATED, "팔로우에 성공했습니다."),
    FOLLOWING_LIST_FETCH_SUCCESS(HttpStatus.OK, "내가 팔로우하고 있는 사용자 목록을 조회에 성공했습니다."),

    //토큰
    ACCESS_TOKEN_REISSUED(HttpStatus.OK,  "액세스 토큰이 성공적으로 재발급되었습니다."),
    ACCESS_TOKEN_VALIDATED(HttpStatus.OK, "액세스 토큰이 유효합니다."),
    REFRESH_TOKEN_VALIDATED(HttpStatus.OK, "리프레시 토큰이 유효합니다."),

    //관리자
    ADMIN_LOGIN_SUCCESS(HttpStatus.OK, "관리자 로그인에 성공했습니다."),
    ADMIN_SIGNUP_SUCCESS(HttpStatus.CREATED, "관리자 회원가입에 성공했습니다."),

    // 이미지
    IMAGE_UPLOAD_SUCCESS(HttpStatus.OK, "이미지가 성공적으로 업로드되었습니다."),

    // 카테고리
    CATEGORY_CREATION_SUCCESS(HttpStatus.CREATED, "카테고리 생성에 성공했습니다."),
    CATEGORY_DELETION_SUCCESS(HttpStatus.OK, "카테고리 삭제에 성공했습니다."),
    CATEGORY_UPDATE_SUCCESS(HttpStatus.OK, "카테고리 업데이트에 성공했습니다."),
    CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "카테고리 조회에 성공했습니다."),
    GENERAL_CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "일반 카테고리 조회에 성공했습니다."),
    GROUP_CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "그룹 카테고리 조회에 성공했습니다."),
    INVITED_CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "초대받은 카테고리 조회에 성공했습니다."),
    CATEGORY_MEMBER_ADDITION_SUCCESS(HttpStatus.OK, "그룹 카테고리에 멤버 추가에 성공했습니다."),
    CATEGORY_MEMBER_REMOVAL_SUCCESS(HttpStatus.OK, "그룹 카테고리에서 멤버 삭제에 성공했습니다."),

    ;

    private final HttpStatus httpStatus;	// HttpStatus
    private final String message;			// 설명
}

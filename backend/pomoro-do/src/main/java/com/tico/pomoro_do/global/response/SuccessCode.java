package com.tico.pomoro_do.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    SUCCESS(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),

    // 사용자
    GOOGLE_LOGIN_SUCCESS(HttpStatus.OK, "구글 로그인을 성공적으로 완료했습니다."),
    GOOGLE_SIGNUP_SUCCESS(HttpStatus.CREATED, "구글 회원가입을 성공적으로 완료했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 및 토큰 삭제를 성공적으로 완료했습니다."),
    USER_FETCH_SUCCESS(HttpStatus.OK, "회원 정보를 성공적으로 조회했습니다."),
    USER_DELETION_SUCCESS(HttpStatus.OK, "회원 탈퇴를 성공적으로 처리했습니다."),
    FOLLOW_SUCCESS(HttpStatus.CREATED, "사용자를 성공적으로 팔로우했습니다."),
    FOLLOWING_LIST_FETCH_SUCCESS(HttpStatus.OK, "내가 팔로우 중인 사용자 목록을 성공적으로 조회했습니다."),
    FOLLOWERS_LIST_FETCH_SUCCESS(HttpStatus.OK, "나를 팔로우 중인 사용자 목록을 성공적으로 조회했습니다."),
    UNFOLLOW_SUCCESS(HttpStatus.OK, "사용자 언팔로우를 성공적으로 완료했습니다."),

    // 토큰
    ACCESS_TOKEN_REISSUED(HttpStatus.OK,  "액세스 토큰을 성공적으로 재발급했습니다."),
    ACCESS_TOKEN_VALIDATED(HttpStatus.OK, "액세스 토큰이 유효함을 확인했습니다."),
    REFRESH_TOKEN_VALIDATED(HttpStatus.OK, "리프레시 토큰이 유효함을 확인했습니다."),

    // 관리자
    ADMIN_LOGIN_SUCCESS(HttpStatus.OK, "관리자 로그인을 성공적으로 완료했습니다."),
    ADMIN_SIGNUP_SUCCESS(HttpStatus.CREATED, "관리자 회원가입을 성공적으로 완료했습니다."),

    // 이미지
    IMAGE_UPLOAD_SUCCESS(HttpStatus.OK, "이미지를 성공적으로 업로드했습니다."),

    // 카테고리
    CATEGORY_CREATION_SUCCESS(HttpStatus.CREATED, "카테고리를 성공적으로 생성했습니다."),
    CATEGORY_DELETION_SUCCESS(HttpStatus.OK, "카테고리를 성공적으로 삭제했습니다."),
    CATEGORY_UPDATE_SUCCESS(HttpStatus.OK, "카테고리를 성공적으로 수정했습니다."),
    CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "카테고리 목록을 성공적으로 조회했습니다."),
    CATEGORY_DETAIL_FETCH_SUCCESS(HttpStatus.OK, "카테고리 상세 정보를 성공적으로 조회했습니다."),

    // 카테고리 조회
    GENERAL_CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "일반 카테고리를 성공적으로 조회했습니다."),
    GROUP_CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "그룹 카테고리를 성공적으로 조회했습니다."),
    INVITED_CATEGORY_FETCH_SUCCESS(HttpStatus.OK, "초대받은 카테고리를 성공적으로 조회했습니다."),

    // 카테고리 초대
    CATEGORY_INVITATION_RESPONSE_SUCCESS(HttpStatus.OK, "카테고리 초대에 성공적으로 응답했습니다."),
    CATEGORY_INVITATION_ACCEPT_SUCCESS(HttpStatus.OK, "카테고리 초대를 성공적으로 수락했습니다."),
    CATEGORY_INVITATION_REJECT_SUCCESS(HttpStatus.OK, "카테고리 초대를 성공적으로 거절했습니다."),

    // 그룹 카테고리
    CATEGORY_MEMBER_ADDITION_SUCCESS(HttpStatus.OK, "그룹 카테고리에 멤버를 성공적으로 추가했습니다."),
    CATEGORY_MEMBER_REMOVAL_SUCCESS(HttpStatus.OK, "그룹 카테고리에서 멤버를 성공적으로 삭제했습니다."),
    LEAVE_GROUP_CATEGORY_SUCCESS (HttpStatus.OK, "그룹 카테고리에서 성공적으로 탈퇴했습니다.")

    ;

    private final HttpStatus httpStatus;	// HttpStatus
    private final String message;			// 설명
}

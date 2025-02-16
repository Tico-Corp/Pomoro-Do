package com.tico.pomoro_do.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 다른 사용자의 프로필 정보를 담는 응답 클래스
 * 공개적으로 표시 가능한 기본 정보와 팔로우 상태를 포함합니다.
 */
@Getter
public class UserProfileResponse {

    private final Long userId; // 사용자 ID
    private final String nickname; // 사용자 닉네임
    private final String profileImageUrl; // 프로필 이미지 URL
    private final boolean following; // 현재 로그인한 사용자가 이 사용자를 팔로우하고 있는지 여부

    @Builder
    public UserProfileResponse(Long userId, String nickname, String profileImageUrl, boolean following) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.following = following;
    }
}
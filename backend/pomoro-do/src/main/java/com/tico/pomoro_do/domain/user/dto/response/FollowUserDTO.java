package com.tico.pomoro_do.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowUserDTO {

    private final Long userId; // 사용자 ID
    private final String nickname; // 사용자 이름
    private final String profileImageUrl; // 프로필 이미지 URL
    private final boolean following; // 현재 로그인한 사용자가 이 사용자를 팔로우하고 있는지 여부

    @Builder
    public FollowUserDTO(Long userId, String nickname, String profileImageUrl, boolean following) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.following = following;
    }
}
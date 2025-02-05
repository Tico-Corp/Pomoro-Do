package com.tico.pomoro_do.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailResponse {

    private final Long userId;

    @Schema(description = "이메일 주소", example = "example@example.com")
    private final String email;

    private final String nickname;

    private final String profileImageUrl;

    // 내가 팔로우하는 사람의 수
    private final int followingCount;

    // 나를 팔로우하는 사람의 수
    private final int followerCount;

    @Builder
    public UserDetailResponse(Long userId, String email, String nickname, String profileImageUrl,
                              int followingCount, int followerCount) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

}

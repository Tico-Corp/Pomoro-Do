package com.tico.pomoro_do.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailDTO {

    private final Long userId;

    @Schema(description = "이메일 주소", example = "example@example.com")
    private final String username;

    private final String nickname;

    private final String profileImageUrl;

    // 내가 팔로우하는 사람의 수
    private final int followingCount;

    // 나를 팔로우하는 사람의 수
    private final int followerCount;

    @Builder
    public UserDetailDTO(Long userId, String username, String nickname, String profileImageUrl,
                         int followingCount, int followerCount) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

}

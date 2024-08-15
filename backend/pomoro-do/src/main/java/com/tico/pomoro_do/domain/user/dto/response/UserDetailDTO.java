package com.tico.pomoro_do.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailDTO {

    private final Long userId;

    @Schema(description = "이메일 주소", example = "example@example.com")
    private final String username;

    private String nickname;

    private String profileImageUrl;

    @Builder
    public UserDetailDTO(Long userId, String username, String nickname, String profileImageUrl) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}

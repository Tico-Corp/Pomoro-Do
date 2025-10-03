package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryMemberResponse {

    private final Long userId; // 그룹멤버의 User ID
    private final String nickname; // 사용자 이름
    private final String profileImageUrl; // 프로필 이미지 URL

    @Builder
    public CategoryMemberResponse(Long userId, String nickname, String profileImageUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}

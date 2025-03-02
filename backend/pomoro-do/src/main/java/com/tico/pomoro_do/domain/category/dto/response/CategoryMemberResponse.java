package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryMemberResponse {

    private final Long groupMemberId; // 그룹멤버 ID
    private final String nickname; // 사용자 이름
    private final String profileImageUrl; // 프로필 이미지 URL

    @Builder
    public CategoryMemberResponse(Long groupMemberId, String nickname, String profileImageUrl) {
        this.groupMemberId = groupMemberId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}

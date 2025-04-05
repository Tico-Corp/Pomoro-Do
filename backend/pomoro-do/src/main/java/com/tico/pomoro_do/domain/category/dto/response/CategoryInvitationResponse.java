package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryInvitationResponse {

    private final Long categoryInvitationId;
    private final String categoryName;
    private final String ownerNickname;

    @Builder
    public CategoryInvitationResponse(Long categoryInvitationId, String categoryName, String ownerNickname) {
        this.categoryInvitationId = categoryInvitationId;
        this.categoryName = categoryName;
        this.ownerNickname = ownerNickname;
    }
}

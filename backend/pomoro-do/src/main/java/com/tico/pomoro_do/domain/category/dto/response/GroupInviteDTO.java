package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupInviteDTO {

    private final Long categoryId;
    private final String title;
    private final String hostNickname;

    @Builder
    public GroupInviteDTO(Long categoryId, String title, String hostNickname) {
        this.categoryId = categoryId;
        this.title = title;
        this.hostNickname = hostNickname;
    }
}

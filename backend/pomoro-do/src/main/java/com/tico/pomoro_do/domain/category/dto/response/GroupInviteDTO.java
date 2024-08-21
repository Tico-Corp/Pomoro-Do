package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;

public class GroupInviteDTO {

    private Long categoryId;
    private String title;
    private String hostNickname;

    @Builder
    public GroupInviteDTO(Long categoryId, String title, String hostNickname) {
        this.categoryId = categoryId;
        this.title = title;
        this.hostNickname = hostNickname;
    }
}

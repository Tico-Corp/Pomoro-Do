package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupInviteDTO {

    private final Long groupMemberId;
    private final String title;
    private final String hostNickname;

    @Builder
    public GroupInviteDTO(Long groupMemberId, String title, String hostNickname) {
        this.groupMemberId = groupMemberId;
        this.title = title;
        this.hostNickname = hostNickname;
    }
}

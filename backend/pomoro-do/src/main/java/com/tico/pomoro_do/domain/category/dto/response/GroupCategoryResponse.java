package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCategoryResponse {

    private Long categoryId;
    private String categoryName;
    private int totalMembers; // 그룹 전체 멤버 수
    private boolean ownerStatus; // 관리자인지 여부

    @Builder
    public GroupCategoryResponse(Long categoryId, String categoryName, int totalMembers, boolean ownerStatus) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalMembers = totalMembers;
        this.ownerStatus = ownerStatus;
    }
}

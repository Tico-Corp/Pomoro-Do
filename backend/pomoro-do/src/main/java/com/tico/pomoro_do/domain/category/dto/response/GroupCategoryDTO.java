package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCategoryDTO {

    private Long categoryId;
    private String title;
    private String color;
    private int memberCount; // 그룹 인원 수

    @Builder
    public GroupCategoryDTO(Long categoryId, String title, String color, int memberCount) {
        this.categoryId = categoryId;
        this.title = title;
        this.color = color;
        this.memberCount = memberCount;
    }
}

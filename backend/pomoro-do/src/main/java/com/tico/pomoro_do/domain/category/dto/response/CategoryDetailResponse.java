package com.tico.pomoro_do.domain.category.dto.response;

import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDetailResponse {

    private Long categoryId;
    private String title;
    private CategoryType type;
    private boolean ownerStatus; // 관리자인지 여부
    private String ownerNickname;
    private CategoryVisibility visibility;
    private List<CategoryMemberResponse> members;
    private int totalMembers; // 그룹 전체 멤버 수
    private String color;

    @Builder
    public CategoryDetailResponse(Long categoryId, String title, CategoryType type,
                                  String ownerNickname, boolean ownerStatus, CategoryVisibility visibility,
                                  List<CategoryMemberResponse> members, int totalMembers, String color
    ) {
        this.categoryId = categoryId;
        this.title = title;
        this.type = type;
        this.ownerNickname = ownerNickname;
        this.ownerStatus = ownerStatus;
        this.visibility = visibility;
        this.members = members;
        this.totalMembers = totalMembers;
        this.color = color;
    }
}

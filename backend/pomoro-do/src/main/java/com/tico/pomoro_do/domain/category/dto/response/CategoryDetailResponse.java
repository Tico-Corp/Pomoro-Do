package com.tico.pomoro_do.domain.category.dto.response;

import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDetailResponse {

    private Long categoryId;
    private String name;
    private CategoryType type;
    private String ownerNickname;
    private boolean ownerFlag;
//    private boolean ownershipStatus; // 관리자인지 여부
    private CategoryVisibility visibility;
    private List<CategoryMemberResponse> members;
    private int totalMembers; // 그룹 전체 멤버 수
//    private String color;

    @Builder
    public CategoryDetailResponse(Long categoryId, String name, CategoryType type,
                                  String ownerNickname, boolean ownerFlag, CategoryVisibility visibility,
                                  List<CategoryMemberResponse> members, int totalMembers
    ) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.ownerNickname = ownerNickname;
        this.ownerFlag = ownerFlag;
        this.visibility = visibility;
        this.members = members;
        this.totalMembers = totalMembers;
    }
}

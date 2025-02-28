package com.tico.pomoro_do.domain.category.dto.response;

import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDetailDTO {

    private Long categoryId;
    private String title;
    private CategoryType type;
    private String hostNickname;
    private boolean hostStatus;
    private CategoryVisibility visibility;
    private List<GroupMemberDTO> members;
    private int memberCount; // 그룹 인원 수
    private String color;

    @Builder
    public CategoryDetailDTO(Long categoryId, String title, CategoryType type,
                             String hostNickname, boolean hostStatus, CategoryVisibility visibility,
                             List<GroupMemberDTO> members, int memberCount, String color
    ) {
        this.categoryId = categoryId;
        this.title = title;
        this.type = type;
        this.hostNickname = hostNickname;
        this.hostStatus = hostStatus;
        this.visibility = visibility;
        this.members = members;
        this.memberCount = memberCount;
        this.color = color;
    }
}

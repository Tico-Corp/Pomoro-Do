package com.tico.pomoro_do.domain.category.dto.response;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryInfoResponse {
    private final Long categoryId;
    private final String name;
    private final CategoryType type;
    private final CategoryVisibility visibility;
    private final int totalMembers;

    public static CategoryInfoResponse of(Category c, int totalMembers) {
        return new CategoryInfoResponse(
                c.getId(), c.getName(), c.getType(), c.getVisibility(), totalMembers
        );
    }
}
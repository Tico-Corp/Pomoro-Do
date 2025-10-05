package com.tico.pomoro_do.domain.category.dto.response;

import com.tico.pomoro_do.domain.category.entity.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitedCategoryInfoResponse {

    private final Long categoryId;
    private final String name;
    private final int totalMembers;

    public static InvitedCategoryInfoResponse of(Category category, int totalMembers) {
        return new InvitedCategoryInfoResponse(
                category.getId(),
                category.getName(),
                totalMembers
        );
    }
}
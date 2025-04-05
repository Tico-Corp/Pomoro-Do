package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonalCategoryResponse {

    private Long categoryId;
    private String categoryName;

    @Builder
    public PersonalCategoryResponse(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}

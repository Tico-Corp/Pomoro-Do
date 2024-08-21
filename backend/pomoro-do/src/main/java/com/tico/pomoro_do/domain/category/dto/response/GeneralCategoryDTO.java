package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GeneralCategoryDTO {

    private Long categoryId;
    private String title;

    @Builder
    public GeneralCategoryDTO(Long categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }
}

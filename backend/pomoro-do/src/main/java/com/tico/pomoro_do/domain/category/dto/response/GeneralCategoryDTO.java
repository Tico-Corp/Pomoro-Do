package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GeneralCategoryDTO {

    private Long categoryId;
    private String title;
    private String color;

    @Builder
    public GeneralCategoryDTO(Long categoryId, String title, String color) {
        this.categoryId = categoryId;
        this.title = title;
        this.color = color;
    }
}

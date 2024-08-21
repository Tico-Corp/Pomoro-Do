package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDTO {

    private List<GeneralCategoryDTO> generalCategories;
    private List<GroupCategoryDTO> groupCategories;

    @Builder
    public CategoryDTO(List<GeneralCategoryDTO> generalCategories, List<GroupCategoryDTO> groupCategories){
        this.generalCategories = generalCategories;
        this.groupCategories = groupCategories;
    }

}

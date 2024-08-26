package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDTO {

    private List<GeneralCategoryDTO> generalCategories;    // 일반 카테고리
    private List<GroupCategoryDTO> groupCategories;        // 그룹 카테고리
    private List<InvitedGroupDTO> invitedGroupCategories;  // 초대받은 그룹 카테고리


    @Builder
    public CategoryDTO(List<GeneralCategoryDTO> generalCategories, List<GroupCategoryDTO> groupCategories, List<InvitedGroupDTO> invitedGroupCategories){
        this.generalCategories = generalCategories;
        this.groupCategories = groupCategories;
        this.invitedGroupCategories = invitedGroupCategories;
    }

}

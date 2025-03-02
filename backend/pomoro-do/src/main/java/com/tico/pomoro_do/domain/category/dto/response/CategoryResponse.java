package com.tico.pomoro_do.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryResponse {

    private List<PersonalCategoryResponse> personalCategories;    // 일반 카테고리
    private List<GroupCategoryResponse> groupCategories;        // 그룹 카테고리
    private List<CategoryInvitationResponse> categoryInvitations;  // 초대받은 그룹 카테고리


    @Builder
    public CategoryResponse(List<PersonalCategoryResponse> personalCategories, List<GroupCategoryResponse> groupCategories, List<CategoryInvitationResponse> categoryInvitations){
        this.personalCategories = personalCategories;
        this.groupCategories = groupCategories;
        this.categoryInvitations = categoryInvitations;
    }

}

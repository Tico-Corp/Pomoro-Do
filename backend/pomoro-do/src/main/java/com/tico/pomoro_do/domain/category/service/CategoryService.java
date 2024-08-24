package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryDetailDTO;
import com.tico.pomoro_do.domain.category.dto.response.CategoryDTO;
import com.tico.pomoro_do.domain.category.dto.response.GeneralCategoryDTO;
import com.tico.pomoro_do.domain.category.dto.response.GroupCategoryDTO;
import com.tico.pomoro_do.domain.category.dto.response.InvitedGroupDTO;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.CategoryType;
import com.tico.pomoro_do.global.enums.CategoryVisibility;

import java.util.List;

public interface CategoryService {

    // 일반/그룹 생성할 때 그룹 멤버까지 생성
    void createCategory(String hostName, CategoryDetailDTO categoryDetailDTO);

    // 카테고리만 생성
    Category createNewCategory(User host, String title, String color, CategoryVisibility visibility, CategoryType type);

    // 일반/그룹 카테고리 조회
    CategoryDTO getCategories(String username);
    // 사용자가 호스트로 있는 일반 카테고리 조회
    List<GeneralCategoryDTO> getGeneralCategories(User host);
    // 사용자가 이미 승낙한 그룹 카테고리 조회
    List<GroupCategoryDTO> getGroupCategories(User user);

    // 초대받은 그룹 카테고리를 최신순으로 가져옴
    List<InvitedGroupDTO> getInvitedGroups(User user);
}

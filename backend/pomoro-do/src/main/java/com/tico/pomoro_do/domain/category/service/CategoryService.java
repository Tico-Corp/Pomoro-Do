package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryDetailDTO;
import com.tico.pomoro_do.domain.category.dto.response.CategoryDTO;
import com.tico.pomoro_do.domain.category.dto.response.GroupInviteDTO;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.CategoryType;
import com.tico.pomoro_do.global.enums.CategoryVisibility;
import com.tico.pomoro_do.global.enums.GroupInvitationStatus;

import java.util.List;

public interface CategoryService {

    // 일반/그룹 생성할 때 그룹 멤버까지 생성
    void createCategory(String hostName, CategoryDetailDTO categoryDetailDTO);

    // 카테고리만 생성
    Category createNewCategory(User host, String title, String color, CategoryVisibility visibility, CategoryType type);

    // 일반/그룹 카테고리 조회
    CategoryDTO getCategories(String username);

    // 초대된 카테고리 조회
    List<GroupInviteDTO> getInvitedGroupCategories(String username, GroupInvitationStatus invitationStatus);
}

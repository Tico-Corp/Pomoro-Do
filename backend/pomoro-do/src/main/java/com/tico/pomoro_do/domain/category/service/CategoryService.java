package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreationDTO;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;

import java.time.LocalDate;
import java.util.List;

public interface CategoryService {

    // 일반/그룹 생성할 때 그룹 멤버까지 생성
    void createCategory(String hostName, CategoryCreationDTO categoryCreationDTO);

    // 카테고리만 생성
    Category createNewCategory(User host, LocalDate date, String title, String color, CategoryVisibility visibility, CategoryType type);

    // 일반/그룹/초대받은 카테고리 조회
    CategoryDTO getCategories(String username);
    // 사용자가 호스트로 있는 일반 카테고리 조회
    List<GeneralCategoryDTO> getGeneralCategories(User host);
    // 사용자가 이미 승낙한 그룹 카테고리 조회
    List<GroupCategoryDTO> getGroupCategories(User user);
    // 초대받은 그룹 카테고리를 최신순으로 가져옴
    List<InvitedGroupDTO> getInvitedGroupCategories(User user);

    // 카테고리 상세 조회
    CategoryDetailDTO getCategoryDetail(Long categoryId, String username);

    // 해당 날짜에 속한 모든 카테고리 조회
    List<Category> findByDate(LocalDate targetDate);

    // 그룹멤버 생성
    void createGroupMember(Category category, User member, CategoryInvitationStatus status, CategoryMemberRole role);
    // 그룹멤버 조회
    List<CategoryMember> findAcceptedMembersByCategory(Category category);
}

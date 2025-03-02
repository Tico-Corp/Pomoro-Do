package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;

import java.time.LocalDate;
import java.util.List;

public interface CategoryService {

    /**
     * 그룹/일반 카테고리 생성
     *
     * @param userId 카테고리 생성 유저 ID
     * @param categoryCreateRequest 생성할 카테고리의 정보가 담긴 DTO
     */
    void createCategory(Long userId, CategoryCreateRequest categoryCreateRequest);

    /**
     * 새로운 카테고리 객체를 생성하고 반환
     *
     * @param owner 카테고리 생성 유저
     * @param startDate 카테고리 생성 날짜
     * @param name 카테고리 이름
     * @param type 카테고리 유형 (개인/그룹)
     * @param visibility 카테고리 공개 설정
     * @return 생성된 카테고리 객체
     */
    Category createNewCategory(User owner, LocalDate startDate, String name, CategoryType type, CategoryVisibility visibility);

    /**
     * 인증된 사용자를 기반으로 사용자의 개인, 그룹, 초대받은 카테고리를 조회
     *
     * @param userId 사용자 ID
     * @return 사용자에 해당하는 개인 카테고리, 그룹 카테고리, 초대받은 그룹 카테고리를 포함하는 CategoryDTO 객체
     */
    CategoryResponse getCategories(Long userId);

    /**
     * 인증된 사용자의 활성화 개인 카테고리 가나다 순으로 조회
     *
     * @param user 사용자
     * @return 사용자의 개인 카테고리를 포함하는 PersonalCategoryResponse 리스트
     */
    List<PersonalCategoryResponse> getPersonalCategories(User user);

    /**
     * 인증된 사용가 속한 그룹 카테고리 가나다 순으로 조회
     *
     * @param user 사용자
     * @return 사용자가 속한 그룹 카테고리를 포함하는 GroupCategoryResponse 리스트
     */
    List<GroupCategoryResponse> getGroupCategories(User user);

    /**
     * 사용자의 카테고리 초대장을 최신순으로 조회
     *
     * @param user 사용자
     * @param status 초대 상태
     * @return 사용자가 초대받은 그룹 카테고리를 포함하는 CategoryInvitationResponse 리스트
     */
    List<CategoryInvitationResponse> getCategoryInvitations(User user, CategoryInvitationStatus status);

    // 카테고리 상세 조회
    CategoryDetailResponse getCategoryDetail(Long categoryId, String username);

    /**
     * 그룹 카테고리 멤버 생성
     *
     * @param category 생성된 카테고리 객체
     * @param member 그룹에 포함될 유저 객체
     * @param role 그룹 내 유저의 역할 (OWNER, MEMBER 등)
     * @param joinedDate 그룹 가입 날짜
     */
    void createCategoryMember(Category category, User member, CategoryMemberRole role, LocalDate joinedDate);
}

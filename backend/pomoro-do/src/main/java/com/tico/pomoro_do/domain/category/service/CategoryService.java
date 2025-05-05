package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryDetailResponse;
import com.tico.pomoro_do.domain.category.dto.response.CategoryInvitationResponse;
import com.tico.pomoro_do.domain.category.dto.response.UserCategoryResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import com.tico.pomoro_do.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface CategoryService {

    /**
     * 개인/그룹 카테고리 생성
     *
     * @param userId 현재 인증된 사용자의 ID
     * @param request 카테고리 생성 요청 DTO
     * @return 생성된 카테고리의 ID
     */
    Long processCategoryCreation(Long userId, CategoryCreateRequest request);

    /**
     * 새로운 카테고리 객체를 생성하고 저장
     *
     * @param owner 카테고리 소유자
     * @param startDate 카테고리 생성 날짜
     * @param name 카테고리 이름
     * @param type 카테고리 유형 (개인/그룹)
     * @param visibility 카테고리 공개 설정
     * @return 저장된 카테고리 객체
     */
    Category createCategory(User owner, LocalDate startDate, String name, CategoryType type, CategoryVisibility visibility);

    /**
     * 유형별 카테고리 조회
     * @param userId 사용자 ID
     * @param type 카테고리 유형 (personal, group, null/기타값은 모든 카테고리)
     * @return 필터링된 카테고리 정보
     */
    UserCategoryResponse getCategories(Long userId, CategoryType type);

    /**
     * 사용자의 카테고리 초대장을 최신순으로 조회
     *
     * @param userId 사용자 ID
     * @param status 초대 상태
     * @return 사용자가 초대받은 그룹 카테고리를 포함하는 CategoryInvitationResponse 리스트
     */
    List<CategoryInvitationResponse> getCategoryInvitationsByStatus(Long userId, CategoryInvitationStatus status);

    /**
     * 카테고리 상세 조회
     *
     * @param categoryId 카테고리 ID
     * @param userId 조회한 유저 ID
     * @return CategoryDetailResponse 객체
     */
    CategoryDetailResponse getCategoryDetail(Long categoryId, Long userId);
}

package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.request.CategoryDeleteRequest;
import com.tico.pomoro_do.domain.category.dto.request.CategoryUpdateRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryDetailResponse;
import com.tico.pomoro_do.domain.category.dto.response.CategoryInfoResponse;
import com.tico.pomoro_do.domain.category.dto.response.UserCategoryResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import com.tico.pomoro_do.domain.user.entity.User;

import java.time.LocalDate;

public interface CategoryService {

    /**
     * 개인/그룹 카테고리 생성
     *
     * @param userId 현재 인증된 사용자의 ID
     * @param request 카테고리 생성 요청 DTO
     * @return 생성된 카테고리의 ID
     */
    CategoryInfoResponse processCategoryCreation(Long userId, CategoryCreateRequest request);

    /**
     * 새로운 카테고리 객체를 생성하고 저장
     *
     * @param owner 카테고리 소유자
     * @param startDate 카테고리 시작일 (기록 시작 기준일)
     * @param name 카테고리 이름
     * @param type 카테고리 유형 (개인/그룹)
     * @param visibility 카테고리 공개 설정
     * @return 저장된 카테고리 객체
     */
    Category createCategory(User owner, LocalDate startDate, String name, CategoryType type, CategoryVisibility visibility);

    /**
     * 유형별 카테고리 조회
     *
     * @param userId 사용자 ID
     * @param type 조회할 카테고리 유형 (PERSONAL, GROUP, null이면 전체 조회)
     * @return 필터링된 카테고리 정보
     */
    UserCategoryResponse getCategories(Long userId, CategoryType type);

    /**
     * 카테고리 상세 조회
     *
     * @param categoryId 카테고리 ID
     * @param userId 조회한 유저 ID
     * @return CategoryDetailResponse 객체
     */
    CategoryDetailResponse getCategoryDetail(Long categoryId, Long userId);

    /**
     * 카테고리 수정
     *
     * @param userId 수정 요청 사용자 ID
     * @param categoryId 수정 대상 카테고리 ID
     * @param request 수정 정보 (카테고리 이름, CategoryVisibility, ...)
     */
    CategoryInfoResponse updateCategory(Long categoryId, Long userId, CategoryUpdateRequest request);

    /**
     * 카테고리 삭제
     *
     * @param userId 삭제 요청 사용자 ID
     * @param categoryId 삭제 대상 카테고리 ID
     * @param request 삭제 정책 정보 (CategoryDeletionOption)
     */
    void deleteCategory(Long userId, Long categoryId, CategoryDeleteRequest request);
}

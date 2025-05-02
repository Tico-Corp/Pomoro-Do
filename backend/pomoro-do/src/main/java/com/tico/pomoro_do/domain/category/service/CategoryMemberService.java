package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.response.CategoryMemberResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
import com.tico.pomoro_do.domain.user.entity.User;

import java.util.List;
import java.util.Map;

public interface CategoryMemberService {

    /**
     * 카테고리 멤버를 생성하고 저장
     *
     * @param category 생성된 카테고리
     * @param member 그룹 멤버 사용자
     * @param role 그룹 내 멤버 역할 (OWNER, MEMBER)
     */
    void createCategoryMember(Category category, User member, CategoryMemberRole role);

    /**
     * 사용자 기준으로 현재 참여 중인 그룹 카테고리 리스트 조회
     *
     * @param user 사용자
     * @return 그룹 카테고리 목록
     */
    List<Category> findUserGroupCategories(User user);

    /**
     * 여러 그룹 카테고리에 대해 활성 멤버 수 계산
     *
     * @param groupCategories 그룹 카테고리 목록
     * @return 카테고리 ID별 멤버 수 맵
     */
    Map<Long, Long> calculateMemberCounts(List<Category> groupCategories);

    /**
     * 그룹 카테고리의 멤버 목록을 정렬된 응답 DTO로 반환
     */
    List<CategoryMemberResponse> getMemberResponses(Category category);

    boolean isMember(Category category, User user);
}

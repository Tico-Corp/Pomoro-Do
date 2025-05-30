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
     * 이미 활동 중인 멤버인 경우에는 저장하지 않습니다.
     *
     * @param category 대상 카테고리
     * @param member 추가할 사용자
     * @param role 그룹 내 역할 (OWNER 또는 MEMBER)
     */
    void createCategoryMember(Category category, User member, CategoryMemberRole role);

    /**
     * 사용자가 현재 참여 중인 그룹 카테고리 목록을 조회
     * (leftDate가 null인 활성 멤버 기준)
     *
     * @param user 사용자
     * @return 사용자가 참여 중인 그룹 카테고리 목록
     */
    List<Category> getActiveCategoriesByUser(User user);

    /**
     * 여러 그룹 카테고리에 대해 현재 활동 중인 멤버 수를 계산
     *
     * @param groupCategories 그룹 카테고리 목록
     * @return 카테고리 ID별 멤버 수 (leftDate=null 기준)
     */
    Map<Long, Long> getActiveMemberCounts(List<Category> groupCategories);

    /**
     * 그룹 카테고리의 활동 중인 멤버 목록을 닉네임 기준으로 정렬된 응답 DTO 반환
     *
     * @param category 그룹 카테고리
     * @return 멤버 응답 DTO 리스트 (닉네임 가나다 순 정렬)
     */
    List<CategoryMemberResponse> getMemberResponses(Category category);

    /**
     * 사용자가 해당 카테고리의 활동 중인 멤버인지 확인
     * (leftDate가 null인 경우 true)
     *
     * @param category 카테고리
     * @param user 사용자
     * @return true: 현재 멤버이며 탈퇴하지 않음, false: 미참여 또는 탈퇴
     */
    boolean isActiveMember(Category category, User user);
}

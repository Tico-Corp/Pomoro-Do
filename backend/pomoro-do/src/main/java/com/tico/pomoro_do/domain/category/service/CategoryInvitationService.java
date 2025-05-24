package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.response.CategoryInvitationResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.user.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 카테고리 초대 도메인 서비스 인터페이스
 * - 초대장 생성, 일괄 초대, 조회 등 초대 도메인 로직 담당
 */
public interface CategoryInvitationService {

    /**
     * 그룹 생성 시 멤버들에게 초대장 일괄 발송
     *
     * @param category 생성된 카테고리
     * @param inviter 초대한 사용자
     * @param memberIds 초대할 사용자 ID 목록
     */
    void inviteMembers(Category category, User inviter, Set<Long> memberIds);

    /**
     * 단건 초대장 생성
     *
     * @param category 초대할 카테고리
     * @param inviter 초대자
     * @param invitee 피초대자
     */
    void createCategoryInvitation(Category category, User inviter, User invitee);

    /**
     * 상태별 초대장 조회 (최신순)
     *
     * @param userId 현재 사용자 ID
     * @param status 초대 상태 (PENDING, ACCEPTED, REJECTED)
     * @return 초대장 응답 리스트
     */
    List<CategoryInvitationResponse> getInvitationsByStatus(Long userId, CategoryInvitationStatus status);

    /**
     * 상태별 초대장 조회 (최신순)
     *
     * @param user 현재 사용자 객체
     * @param status 초대 상태 (PENDING, ACCEPTED, REJECTED)
     * @return 초대장 응답 리스트
     */
    List<CategoryInvitationResponse> findInvitationsByStatus(User user, CategoryInvitationStatus status);
}

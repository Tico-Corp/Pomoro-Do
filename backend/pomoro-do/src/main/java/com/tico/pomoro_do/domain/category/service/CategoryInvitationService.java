package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryInvitationDecisionRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryInvitationResponse;
import com.tico.pomoro_do.domain.category.dto.response.InvitedCategoryInfoResponse;
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
     * 그룹 생성 시 초대 대상 사용자들에게 초대장을 일괄 발송
     * - 소유자 본인은 제외됩니다.
     * - 초대자가 팔로우 중인 사용자만 초대 가능합니다.
     * - 이미 초대되었거나 그룹 멤버인 사용자는 제외됩니다.
     *
     * @param category 그룹 카테고리
     * @param inviter 초대한 사용자 (카테고리 소유자)
     * @param memberIds 초대 대상 사용자 ID 목록
     */
    void inviteMembers(Category category, User inviter, Set<Long> memberIds);

    /**
     * 특정 사용자에게 단일 초대장을 생성
     * - 이미 초대되었거나 멤버인 경우에는 초대장이 생성되지 않습니다.
     *
     * @param category 그룹 카테고리
     * @param inviter 초대한 사용자
     * @param invitee 초대 대상 사용자
     */
    void createCategoryInvitation(Category category, User inviter, User invitee);

    /**
     * 사용자 ID를 기준으로 상태별 초대장을 조회 (최신순)
     * - 주로 컨트롤러에서 JWT 인증된 사용자 ID를 사용할 때 호출합니다.
     *
     * @param userId 현재 로그인한 사용자 ID
     * @param status 초대 상태 (PENDING, ACCEPTED, REJECTED)
     * @return 초대장 응답 DTO 리스트
     */
    List<CategoryInvitationResponse> getInvitationsByStatus(Long userId, CategoryInvitationStatus status);

    /**
     * 사용자 객체를 기준으로 상태별 초대장을 조회 (최신순)
     * - 내부 서비스 로직에서 사용자 객체를 활용할 때 사용합니다.
     *
     * @param user 사용자 객체
     * @param status 초대 상태 (PENDING, ACCEPTED, REJECTED)
     * @return 초대장 응답 DTO 리스트
     */
    List<CategoryInvitationResponse> findInvitationsByStatus(User user, CategoryInvitationStatus status);

    /**
     * 초대장에 대한 응답 처리 (수락 또는 거절)
     * - 초대장 존재 여부와 응답 권한을 검증합니다.
     * - 수락 시 그룹 멤버로 자동 등록됩니다.
     *
     * @param invitationId 응답할 초대장 ID
     * @param userId 응답하는 사용자 ID
     * @param request 응답 요청 객체 (ACCEPTED 또는 REJECTED)
     */
    InvitedCategoryInfoResponse respondInvitation(Long invitationId, Long userId, CategoryInvitationDecisionRequest request);

    /**
     * 해당 카테고리에 대한 응답되지 않은(PENDING) 초대장 모두 삭제
     * - 그룹 카테고리 삭제 시 호출됩니다.
     * - 수락 또는 거절되지 않은 초대장만 대상으로 합니다.
     *
     * @param category 초대장을 삭제할 대상 카테고리
     */
    void deleteAllPendingInvitations(Category category);
}

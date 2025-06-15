package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryInvitationDecisionRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryInvitationResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryInvitation;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
import com.tico.pomoro_do.domain.category.repository.CategoryInvitationRepository;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.FollowService;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회 기본 설정
@Slf4j
public class CategoryInvitationServiceImpl implements CategoryInvitationService{

    private final UserService userService;
    private final FollowService followService;
    private final CategoryMemberService categoryMemberService;
    private final CategoryInvitationRepository categoryInvitationRepository;

    /**
     * 그룹 생성 시 일괄 초대장을 생성
     * - 소유자 자신은 제외
     * - 초대자가 팔로우 중인 사용자만 초대 가능
     * - 이미 멤버이거나 초대된 대상은 제외
     *
     * @param category 대상 그룹 카테고리
     * @param inviter 초대자 (카테고리 생성자)
     * @param memberIds 초대 대상 사용자 ID 목록
     */
    public void inviteMembers(Category category, User inviter, Set<Long> memberIds) {
        // 1. 멤버가 없으면 처리 종료
        // 그룹 카테고리를 생성할 때 추가 안 할 수 도 있음
        if (memberIds == null || memberIds.isEmpty()) {
            log.debug("초대할 멤버가 없음: categoryId={}", category.getId());
            return;
        }
        // 소유자 자신은 초대 목록에서 제거 (중복 방지)
        memberIds.remove(inviter.getId());
        if (memberIds.isEmpty()) {
            log.debug("소유자 제외 후 초대할 멤버 없음: categoryId={}", category.getId());
            return;
        }

        // 2. 초대자의 팔로우 목록만 허용
        // 팔로우 관계 일괄 조회 (N+1 문제 방지)
        Set<Long> followingIds = followService.getFollowingIdsByUser(inviter.getId());
        // 팔로우 중인 사용자만 필터링
        Set<Long> followedMemberIds = memberIds.stream()
                .filter(followingIds::contains)
                .collect(Collectors.toSet());
        // 팔로우 중인 사용자 없으면 처리 종료
        if (followedMemberIds.isEmpty()) {
            log.warn("팔로우 중인 초대 대상자 없음: categoryId={}", category.getId());
            return;
        }

        // 3. 사용자 팔로우 정보 일괄 조회: ID → 사용자 객체 맵 조회 (N+1 방지)
        Map<Long, User> inviteeMap = userService.findUsersByIds(followedMemberIds);

        // 4. 유효한 사용자에 대해 초대장 생성
        List<CategoryInvitation> invitations = createCategoryInvitations(category, inviter, followedMemberIds, inviteeMap);

        // 5. 초대장 일괄 저장 (단일 쿼리로 처리)
        if (!invitations.isEmpty()) {
            categoryInvitationRepository.saveAll(invitations);
            log.info("그룹 멤버 초대 완료: categoryId={}, 초대 수={}", category.getId(), invitations.size());
        }
    }

    /**
     * 유효한 사용자에 대해 초대장을 생성
     * - 조건:
     *   1. 존재하는 사용자 (userMap 기준)
     *   2. 현재 멤버가 아닌 사용자 (isActiveMember=false)
     *   3. PENDING 상태 초대장이 없는 사용자
     *
     * @param category 그룹 카테고리
     * @param inviter 초대한 사용자
     * @param inviteeIds 초대 대상 사용자 ID 목록
     * @param userMap 사용자 ID → User 객체 맵
     * @return 생성된 초대장 엔티티 리스트
     */
    private List<CategoryInvitation> createCategoryInvitations(
            Category category, User inviter, Set<Long> inviteeIds, Map<Long, User> userMap) {

        return inviteeIds.stream()
                .map(userMap::get)
                .filter(Objects::nonNull) // 팔로우 한 사용자만 가져오기
                .filter(invitee -> !categoryMemberService.isActiveMember(category, invitee)) // 현재 멤버 제외
                .filter(invitee -> !hasPendingInvitation(category, invitee)) // 중복 초대 방지 (PENDING 상태 존재 여부)
                .map(invitee -> CategoryInvitation.builder()
                        .category(category)
                        .inviter(inviter)
                        .invitee(invitee)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 단일 사용자에 대한 초대장 생성
     * 이미 초대되었거나 멤버인 경우에는 생성하지 않습니다.
     *
     * @param category 그룹 카테고리
     * @param inviter 초대한 사용자
     * @param invitee 초대 대상 사용자
     */
    @Override
    @Transactional
    public void createCategoryInvitation(Category category, User inviter, User invitee) {
        // 이미 초대장이 존재하는지 확인 (중복 초대 방지-> 미응답 초대장 존재 여부)
        if (hasPendingInvitation(category, invitee)) {
            log.warn("이미 대기 중인 초대장이 존재: categoryId={}, inviteeId={}", category.getId(), invitee.getId());
            return;
        }

        // 이미 멤버인지 확인 (여기서 이전에 수락한 초대장 중 현재 멤버 여부 확인)
        if (categoryMemberService.isActiveMember(category, invitee)) {
            log.warn("이미 멤버인 사용자에게 초대장 생성 시도: categoryId={}, inviteeId={}", category.getId(), invitee.getId());
            return;
        }

        // 초대장 객체 생성
        CategoryInvitation categoryInvitation = CategoryInvitation.builder()
                .category(category)
                .inviter(inviter)
                .invitee(invitee)
                .build();

        // 저장
        categoryInvitationRepository.save(categoryInvitation);
        log.debug("단건 초대 완료: categoryId={}, inviterId={}, inviteeId={}",
                category.getId(), inviter.getId(), invitee.getId());
    }

    /**
     * 사용자 ID를 기반으로 초대장 상태별 조회
     * 내부적으로 사용자 객체를 조회한 후 필터링된 결과를 반환합니다.
     *
     * @param userId 사용자 ID
     * @param status 초대 상태 (PENDING, ACCEPTED 등)
     * @return 초대장 응답 리스트 (최신순)
     */
    @Override
    public List<CategoryInvitationResponse> getInvitationsByStatus(Long userId, CategoryInvitationStatus status) {
        // 사용자 조회
        User user = userService.findUserById(userId);
        return findInvitationsByStatus(user, status);
    }

    /**
     * 사용자 객체를 기반으로 상태별 초대장 조회 (최신순)
     *
     * @param user 사용자
     * @param status 초대 상태
     * @return 초대장 응답 리스트
     */
    @Override
    public List<CategoryInvitationResponse> findInvitationsByStatus(User user, CategoryInvitationStatus status) {
        // 초대 상태에 따른 초대장 최신순으로 조회 (ex, invitee=user, CategoryInvitationStatus=PENDING)
        List<CategoryInvitation> categoryInvitations = categoryInvitationRepository.findAllByInviteeAndStatusOrderByCreatedAtDesc(user, status);

        return categoryInvitations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    /**
     * CategoryInvitation 엔티티를 CategoryInvitationResponse DTO로 변환
     *
     * @param categoryInvitation 초대장
     * @return 초대장 응답 DTO
     */
    private CategoryInvitationResponse toResponse(CategoryInvitation categoryInvitation) {
        return CategoryInvitationResponse.builder()
                .categoryInvitationId(categoryInvitation.getId())
                .categoryName(categoryInvitation.getCategory().getName())
                .ownerNickname(categoryInvitation.getInviter().getNickname())
                .build();
    }

    /**
     * 사용자가 해당 카테고리에 대해 '대기 중(PENDING)' 상태의 초대장이 있는지 확인
     * - PENDING 상태의 초대가 존재할 경우, 중복 초대를 방지하기 위해 true를 반환합니다.
     *
     * @param category 카테고리
     * @param invitee 피초대자
     * @return PENDING 상태의 초대장이 존재하면 true, 그렇지 않으면 false
     */
    private boolean hasPendingInvitation(Category category, User invitee) {
        return categoryInvitationRepository.existsByCategoryAndInviteeAndStatus(
                category,
                invitee,
                CategoryInvitationStatus.PENDING
        );
    }

    /**
     * 초대장에 대한 응답 처리 (수락 or 거절)
     * - 초대장 존재 여부 확인
     * - 응답 사용자의 권한 검증
     * - 초대장 상태 변경
     * - 수락 시 그룹 멤버로 등록
     *
     * @param invitationId 응답할 초대장 ID
     * @param userId 현재 로그인한 사용자 ID
     * @param request 응답 요청 (수락/거절)
     */
    @Override
    @Transactional
    public void respondInvitation(Long invitationId, Long userId, CategoryInvitationDecisionRequest request) {
        // 1. 초대장 조회
        CategoryInvitation invitation = getInvitationById(invitationId);
        // 2. 초대 대상자 본인인지 확인
        validateInvitee(invitation, userId);

        // 3. 요청 값(ACCEPTED/REJECTED)을 도메인 상태로 변환
        CategoryInvitationStatus status = switch (request.getDecision()) {
            case ACCEPTED -> CategoryInvitationStatus.ACCEPTED;
            case REJECTED -> CategoryInvitationStatus.REJECTED;
        };

        // 4. 상태 변경 (엔티티 내부에서 상태값 변경)
        invitation.respondToInvitation(status);

        // 5. 수락한 경우 그룹 멤버로 등록
        if (status == CategoryInvitationStatus.ACCEPTED) {
            categoryMemberService.createCategoryMember(
                    invitation.getCategory(),
                    invitation.getInvitee(),
                    CategoryMemberRole.MEMBER
            );
            log.info("초대 수락: 그룹 멤버 생성 완료 - categoryId={}, userId={}",
                    invitation.getCategory().getId(), invitation.getInvitee().getId());
        }
    }

    /**
     * 초대장 ID로 초대 정보를 조회합니다.
     * 존재하지 않는 경우 예외를 발생시킵니다.
     *
     * @param invitationId 초대장 ID
     * @return CategoryInvitation 객체
     * @throws CustomException 초대장이 존재하지 않을 경우
     */
    private CategoryInvitation getInvitationById(Long invitationId) {
        return categoryInvitationRepository.findById(invitationId)
                .orElseThrow(() -> {
                    log.warn("초대장 조회 실패: 존재하지 않음 - invitationId={}", invitationId);
                    return new CustomException(ErrorCode.INVITATION_NOT_FOUND);
                });
    }

    /**
     * 응답 사용자가 실제 초대 대상자인지 검증합니다.
     *
     * @param invitation 초대장
     * @param userId 응답자 ID
     * @throws CustomException 본인이 아닌 경우
     */
    private void validateInvitee(CategoryInvitation invitation, Long userId) {
        Long inviteeId = invitation.getInvitee().getId();
        if (!inviteeId.equals(userId)) {
            log.warn("초대 대상자 불일치: invitationId={}, expectedInviteeId={}, actualUserId={}",
                    invitation.getId(), inviteeId, userId);
            throw new CustomException(ErrorCode.NOT_INVITEE);
        }
    }

    /**
     * 해당 카테고리에 대한 응답되지 않은(PENDING) 초대장을 모두 삭제합니다.
     * - 그룹 카테고리 삭제 시 호출
     * - 수락 또는 거절되지 않은 초대장만 대상
     *
     * @param category 초대장을 삭제할 대상 카테고리
     */
    @Override
    public void deleteAllPendingInvitations(Category category) {
        categoryInvitationRepository.deleteAllByCategoryAndStatus(category, CategoryInvitationStatus.PENDING);
    }
}
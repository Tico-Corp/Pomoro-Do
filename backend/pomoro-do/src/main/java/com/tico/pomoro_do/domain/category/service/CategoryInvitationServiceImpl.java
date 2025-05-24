package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.response.CategoryInvitationResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryInvitation;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.repository.CategoryInvitationRepository;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.FollowService;
import com.tico.pomoro_do.domain.user.service.UserService;
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
@Transactional(readOnly = true)
@Slf4j
public class CategoryInvitationServiceImpl implements CategoryInvitationService{

    private final UserService userService;
    private final FollowService followService;
    private final CategoryMemberService categoryMemberService;
    private final CategoryInvitationRepository categoryInvitationRepository;

    /**
     * 그룹 생성 시 일괄 초대 처리 로직
     * - 소유자 제외
     * - 팔로우 중인 사용자만 초대
     * - 이미 초대되었거나 멤버인 경우 제외
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
            log.debug("초대 대상에서 소유자 제외 후 초대할 멤버 없음: categoryId={}", category.getId());
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
     * 다건 초대장 생성 (다수의 초대 받은 사람들에게 초대장을 한꺼번에 생성)
     */
    private List<CategoryInvitation> createCategoryInvitations(
            Category category, User inviter, Set<Long> inviteeIds, Map<Long, User> userMap) {

        return inviteeIds.stream()
                .map(userMap::get)
                .filter(Objects::nonNull) // 팔로우 한 사용자만 가져오기
                .filter(invitee -> !categoryMemberService.isActiveMember(category, invitee)) // 기존 멤버 제외
                .map(invitee -> CategoryInvitation.builder()
                        .category(category)
                        .inviter(inviter)
                        .invitee(invitee)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 단건 초대장 생성
     */
    @Override
    @Transactional
    public void createCategoryInvitation(Category category, User inviter, User invitee) {
        // 이미 초대장이 존재하는지 확인 (중복 초대 방지)
        if (categoryInvitationRepository.existsByCategoryAndInvitee(category, invitee)) {
            log.warn("이미 초대장이 존재: categoryId={}, inviteeId={}", category.getId(), invitee.getId());
            return;
        }

        // 이미 멤버인지 확인
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
     * userId 기반 초대장 상태별 조회
     */
    @Override
    public List<CategoryInvitationResponse> getInvitationsByStatus(Long userId, CategoryInvitationStatus status) {
        // 사용자 조회
        User user = userService.findUserById(userId);
        return findInvitationsByStatus(user, status);
    }

    /**
     * 사용자 + 상태 기반 초대장 조회 (최신순)
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
     * 초대장 정보를 CategoryInvitationResponse로 변환
     *
     * @param categoryInvitation 카테고리 초대장
     * @return CategoryInvitationResponse 객체
     */
    private CategoryInvitationResponse toResponse(CategoryInvitation categoryInvitation) {
        return CategoryInvitationResponse.builder()
                .categoryInvitationId(categoryInvitation.getId())
                .categoryName(categoryInvitation.getCategory().getName())
                .ownerNickname(categoryInvitation.getInviter().getNickname())
                .build();
    }
}
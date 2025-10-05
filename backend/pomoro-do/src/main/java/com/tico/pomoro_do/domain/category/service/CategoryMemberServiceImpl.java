package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryLeaveRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryMemberResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.category.enums.CategoryDeletionOption;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
import com.tico.pomoro_do.domain.category.repository.CategoryMemberRepository;
import com.tico.pomoro_do.domain.category.validator.CategoryValidator;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryMemberServiceImpl implements CategoryMemberService {

    private final CategoryMemberRepository categoryMemberRepository;
    private final CategoryValidator categoryValidator;

    @Override
    @Transactional
    public void leaveGroupCategory(Long userId, Long categoryId, CategoryLeaveRequest request) {
        // 1. 카테고리 검증 및 조회
        Category category = categoryValidator.validateExists(categoryId);

        // 2. 그룹 카테고리 여부 검증
        if (!category.isGroup()) {
            throw new CustomException(ErrorCode.NOT_GROUP_CATEGORY);
        }

        // 3. 현재 유저의 카테고리 멤버 여부 검증 및 조회
        CategoryMember member = categoryMemberRepository.findByCategoryIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_MEMBER_NOT_ACTIVE));

        // 4. 이미 탈퇴한 멤버인지 검증
        if (member.getLeftDate() != null) {
            throw new CustomException(ErrorCode.ALREADY_LEFT_CATEGORY);
        }

        // 5. 그룹장일 경우 → 위임 처리
        if (member.isOwner()) {
            // 5-1. 유효한 멤버 목록 조회 (leftDate == null, 닉네임 오름차순 정렬)
            List<CategoryMember> activeMembers = categoryMemberRepository
                    .findByCategoryAndLeftDateIsNullOrderByUserNicknameAsc(category);

            // 5-2. 그룹장이 유일한 멤버일 경우 → 탈퇴 불가
            if (activeMembers.size() == 1 && activeMembers.get(0).equals(member)) {
                log.warn("마지막 그룹장은 탈퇴할 수 없습니다. [categoryId={}, userId={}]", categoryId, userId);
                throw new CustomException(ErrorCode.GROUP_OWNER_CANNOT_LEAVE_WHEN_ALONE);
            }

            // 5-3. 다음 멤버에게 그룹장 위임
            assignNextOwner(category, member, activeMembers);
        }

        // 6. 탈퇴 처리 (그룹장/일반 멤버 공통 처리)
        member.leave(request.getDeletionOption());

        log.info("그룹 멤버 탈퇴 완료. [categoryId={}, userId={}]", categoryId, userId);

    }

    /**
     * 현재 그룹장을 제외한 유효 멤버 중 닉네임 오름차순 첫 번째 사용자에게 그룹장 권한을 위임합니다.
     *
     * @param category      대상 그룹 카테고리
     * @param currentOwner  현재 그룹장 (탈퇴 전 상태)
     * @param activeMembers leftDate == null 상태의 유효 멤버 목록 (닉네임 오름차순 정렬)
     */
    private void assignNextOwner(Category category, CategoryMember currentOwner, List<CategoryMember> activeMembers) {
        // 1. 위임 대상 선정 (자기 자신 제외)
        CategoryMember newOwner = activeMembers.stream()
                .filter(m -> !m.getId().equals(currentOwner.getId()))
                .findFirst()
                .orElseThrow(() -> {
                    // 이 지점까지 왔다면 로직상 위임 대상이 반드시 있어야 함
                    log.error("그룹장 위임 실패 - 위임 대상이 없습니다. [categoryId={}, currentOwnerId={}]",
                            category.getId(), currentOwner.getUser().getId());
                    return new CustomException(ErrorCode.GROUP_OWNER_ASSIGNMENT_FAILED);
                });

        // 2. 현재 그룹장 → 일반 멤버로 역할 변경
        currentOwner.updateRole(CategoryMemberRole.MEMBER);
        log.info("그룹장 역할 해제. [categoryId={}, userId={}]",
                category.getId(), currentOwner.getUser().getId());

        // 3. 새 멤버 → 그룹장으로 역할 위임
        newOwner.updateRole(CategoryMemberRole.OWNER);
        category.updateOwner(newOwner.getUser());

        log.info("그룹장 위임 완료. [categoryId={}, newOwnerId={}, newOwnerNickname={}]",
                category.getId(),
                newOwner.getUser().getId(),
                newOwner.getUser().getNickname());
    }

    /**
     * 카테고리에 멤버 등록
     * 이미 활동 중인 멤버인 경우 등록하지 않습니다.
     *
     * @param category 대상 카테고리
     * @param user 추가할 사용자
     * @param role 사용자 역할 (OWNER 또는 MEMBER)
     */
    @Override
    public void createCategoryMember(Category category, User user, CategoryMemberRole role) {
        // 중복 멤버 확인
        if (isActiveMember(category, user)) {
            log.warn("이미 등록된 멤버입니다. categoryId={}, userId={}", category.getId(), user.getId());
            return;
        }

        // 카테고리 멤버 생성 및 저장
        CategoryMember member = CategoryMember.builder()
                .category(category)
                .user(user)
                .role(role)
                .build();

        // 저장
        categoryMemberRepository.save(member);

        log.debug("멤버 등록 완료: categoryId={}, userId={}, role={}",
                category.getId(), user.getId(), role);
    }

    /**
     * 사용자가 현재 참여 중인 그룹 카테고리 목록을 조회
     *
     * @param user 대상 사용자
     * @return 사용자가 참여 중인 그룹 카테고리 목록 (leftDate가 null인 멤버 기준)
     */
    @Override
    public List<Category> getActiveCategoriesByUser(User user) {
        // 카테고리 멤버로 활성회되어있는 그룹 카테고리 조회 (user=user, leftDate=null)
        List<CategoryMember> categoryMembers = categoryMemberRepository.findAllByUserAndLeftDateIsNull(user);
        return categoryMembers.stream()
                .map(CategoryMember::getCategory)
                .collect(Collectors.toList());
    }

    /**
     * 해당 그룹 카테고리에 대해 현재 참여 중인 멤버 수 조회
     *
     * @param categoryId 대상 그룹 카테고리 ID
     * @return 그룹 멤버 수
     */
    @Override
    public int countActiveMembers(Long categoryId) {
        // 필요 시 categoryId 유효성 검증/권한 체크 추가 가능
        return categoryMemberRepository.countByCategoryIdAndLeftDateIsNull(categoryId);
    }

    /**
     * 여러 그룹 카테고리에 대해 현재 참여 중인 멤버 수 조회
     *
     * @param groupCategories 대상 그룹 카테고리 목록
     * @return 카테고리 ID → 멤버 수 Map
     */
    @Override
    public Map<Long, Long> getActiveMemberCounts(List<Category> groupCategories) {
        // 모든 카테고리의 멤버 수를 한 번에 가져와서 Map으로 변환 (List<Category>=groupCategories, leftDate=null)
        List<CategoryMember> activeMembers =
                categoryMemberRepository.findAllByCategoryInAndLeftDateIsNull(groupCategories);
        return activeMembers.stream()
                .collect(Collectors.groupingBy(
                        categoryMember -> categoryMember.getCategory().getId(),  // 카테고리 ID 기준 그룹화
                        Collectors.counting()      // 각 카테고리의 멤버 수 계산
                ));
    }

    /**
     * 그룹 카테고리의 활동 중인 멤버 목록을 닉네임 기준으로 정렬하여 반환
     *
     * @param category 대상 카테고리
     * @return 멤버 응답 DTO 목록 (닉네임 가나다 순 정렬)
     */
    @Override
    public List<CategoryMemberResponse> getMemberResponses(Category category) {
        List<CategoryMember> categoryMembers = categoryMemberRepository.findAllByCategoryAndLeftDateIsNull(category);

        return categoryMembers.stream()
                .map(member -> CategoryMemberResponse.builder()
                        .userId(member.getUser().getId())
                        .nickname(member.getUser().getNickname())
                        .profileImageUrl(member.getUser().getProfileImageUrl())
                        .build())
                .sorted(Comparator.comparing(CategoryMemberResponse::getNickname))
                .collect(Collectors.toList());
    }

    /**
     * 해당 사용자가 해당 카테고리의 활동 중인 멤버인지 확인
     *
     * @param category 카테고리
     * @param user 사용자
     * @return true: 현재 멤버이며 탈퇴하지 않음, false: 아니거나 탈퇴함
     */
    @Override
    public boolean isActiveMember(Category category, User user) {
        return categoryMemberRepository.existsByCategoryAndUserAndLeftDateIsNull(category, user);
    }

    /**
     * 카테고리 삭제 시 그룹 카테고리의 활동 중인 멤버 전원 탈퇴
     * - leftDate와 삭제 정책 갱신
     *
     * @param category 탈퇴시킬 그룹 카테고리
     * @param deletionOption 각 멤버에게 적용할 데이터 삭제 정책
     */
    @Override
    public void leaveAllActiveMembers(Category category, CategoryDeletionOption deletionOption) {
        List<CategoryMember> activeMembers  = categoryMemberRepository.findAllByCategoryAndLeftDateIsNull(category);

        log.info("카테고리 탈퇴 처리 시작 - categoryId={}, memberCount={}, deletionOption={}",
                category.getId(), activeMembers.size(), deletionOption);

        for (CategoryMember member : activeMembers) {
            log.debug("멤버 탈퇴 처리 - memberId={}, userId={}, deletionOption={}",
                    member.getId(), member.getUser().getId(), deletionOption);

            member.leave(deletionOption);
        }

        log.info("카테고리 멤버 탈퇴 완료 - categoryId={}, 처리된 멤버 수={}", category.getId(), activeMembers.size());
    }
}
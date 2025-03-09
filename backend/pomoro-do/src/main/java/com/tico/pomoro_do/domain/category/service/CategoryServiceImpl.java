package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryInvitation;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import com.tico.pomoro_do.domain.category.repository.CategoryInvitationRepository;
import com.tico.pomoro_do.domain.category.repository.CategoryMemberRepository;
import com.tico.pomoro_do.domain.category.repository.CategoryRepository;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.FollowService;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final UserService userService;
    private final FollowService followService;
    private final CategoryRepository categoryRepository;
    private final CategoryMemberRepository categoryMemberRepository;
    private final CategoryInvitationRepository categoryInvitationRepository;

    // 개인/그룹 카테고리 생성 -> 그룹 카테고리이면, 생성자는 관리자 멤버로 생성하고, 초대장 발송
    @Override
    @Transactional
    public Long processCategoryCreation(Long userId, CategoryCreateRequest request) {
        // 1. 사용자 조회
        User owner = userService.findUserById(userId);
        // 2. 개인/그룹 카테고리 생성
        Category category = createCategory(
                owner,
                request.getStartDate(),
                request.getCategoryName(),
                request.getCategoryType(),
                request.getCategoryVisibility()
        );

        // 그룹 카테고리 유형에 따른 추가 로직 처리
        if (CategoryType.GROUP.equals(request.getCategoryType())) {
            // 그룹 멤버 및 초대장 처리 - N+1 문제 개선 버전
            processGroupCategoryMembers(category, owner, request.getGroupMemberIds());
        }

        log.info("카테고리 생성 완료: id={}, type={}, name={}, owner={}",
                category.getId(), category.getType(), category.getName(), owner.getId());

        return category.getId();
    }

    // 새로운 카테고리 생성
    @Override
    public Category createCategory(User owner, LocalDate startDate, String name, CategoryType type, CategoryVisibility visibility) {
        // 카테고리 빌더로 객체 생성
        Category category = Category.builder()
                .owner(owner)
                .startDate(startDate)
                .name(name)
                .type(type)
                .visibility(visibility)
                .build();
        // 저장 후 반환
        return categoryRepository.save(category);
    }


    /**
     * 그룹 카테고리의 멤버들을 처리하는 최적화된 메서드
     * 1. 소유자(생성자)를 그룹 멤버로 추가
     * 2. 유효한 멤버 ID 필터링을 위해 팔로우 관계를 한 번에 조회
     * 3. 초대할 사용자 정보를 한 번에 조회
     * 4. 초대장 일괄 생성 및 저장
     *
     * @param category 생성된 카테고리 객체
     * @param owner 카테고리 소유자
     * @param memberIds 그룹 카테고리에 초대할 멤버들의 ID 집합
     */
    private void processGroupCategoryMembers(Category category, User owner, Set<Long> memberIds) {
        // 1. 소유자(그룹장)를 그룹 멤버로 추가
        createCategoryMember(category, owner, CategoryMemberRole.OWNER);
        log.info("그룹 소유자 등록 완료: categoryId={}, ownerId={}", category.getId(), owner.getId());

        // 2. 초대 멤버 리스트에서 소유자 ID 제거 (중복 방지)
        memberIds.remove(owner.getId());

        // 그룹 카테고리를 생성할 때 추가 안 할 수 도 있음
        if (memberIds.isEmpty()) {
            log.info("초대할 멤버가 없습니다.");
            return;
        }

        // 3. 팔로우 관계를 한 번에 조회 (N+1 문제 해결)
        Set<Long> followingIds = followService.getFollowingIdsByUser(owner.getId());

        // 4. 팔로우 중인 사용자만 필터링
        Set<Long> validMemberIds = memberIds.stream()
                .filter(followingIds::contains)
                .collect(Collectors.toSet());

        if (validMemberIds.isEmpty()) {
            log.warn("팔로우 중인 사용자가 없어 초대할 수 없습니다.");
            return;
        }

        // 5. 유효한 사용자 정보를 한 번에 조회 (N+1 문제 해결)
        Map<Long, User> userMap = userService.findUsersByIds(validMemberIds);

        // 6. 초대장 일괄 생성
        List<CategoryInvitation> invitations = new ArrayList<>();
        for (Long memberId : validMemberIds) {
            User invitee = userMap.get(memberId);
            if (invitee == null) {
                log.warn("사용자 정보를 찾을 수 없음: memberId={}", memberId);
                continue;
            }

            // 초대장 생성
            CategoryInvitation invitation = CategoryInvitation.builder()
                    .category(category)
                    .inviter(owner)
                    .invitee(invitee)
                    .build();

            invitations.add(invitation);
        }

        // 7. 초대장 일괄 저장 (단일 쿼리로 처리)
        if (!invitations.isEmpty()) {
            categoryInvitationRepository.saveAll(invitations);
            log.info("그룹 멤버 초대 완료: categoryId={}, invitationCount={}",
                    category.getId(), invitations.size());
        }
    }

    // 카테고리 멤버를 생성하고 저장
    @Override
    public void createCategoryMember(Category category, User member, CategoryMemberRole role) {
        // 이미 등록된 멤버인지 확인 (중복 등록 방지)
        if (categoryMemberRepository.existsByCategoryAndUser(category, member)) {
            log.warn("이미 카테고리에 등록된 멤버: categoryId={}, userId={}", category.getId(), member.getId());
            return;
        }

        // 카테고리 멤버 객체 생성
        CategoryMember categoryMember = CategoryMember.builder()
                .category(category)
                .user(member)
                .role(role)
                .build();

        // 저장
        categoryMemberRepository.save(categoryMember);
        log.debug("카테고리 멤버 등록 완료: categoryId={}, userId={}, role={}",
                category.getId(), member.getId(), role);
    }

    /**
     * 그룹 카테고리 초대장을 생성하고 저장하는 메서드
     *
     * @param category 초대하는 카테고리
     * @param inviter 초대하는 사용자
     * @param invitee 초대받는 사용자
     */
    private void createCategoryInvitation(Category category, User inviter, User invitee) {
        // 이미 초대장이 존재하는지 확인 (중복 초대 방지)
        if (categoryInvitationRepository.existsByCategoryAndInvitee(category, invitee)) {
            log.warn("이미 초대장이 발송된 사용자: categoryId={}, inviteeId={}", category.getId(), invitee.getId());
            return;
        }

        // 이미 카테고리 멤버인지 확인
        if (categoryMemberRepository.existsByCategoryAndUser(category, invitee)) {
            log.warn("이미 카테고리 멤버인 사용자: categoryId={}, inviteeId={}", category.getId(), invitee.getId());
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
        log.debug("카테고리 초대장 발송 완료: categoryId={}, inviterId={}, inviteeId={}",
                category.getId(), inviter.getId(), invitee.getId());
    }

    // 일반/그룹/초대받은 카테고리 조회
    @Override
    public UserCategoryResponse getCategories(Long userId) {
        // 사용자 조회
        User user = userService.findUserById(userId);

        // 개인 카테고리
        List<PersonalCategoryResponse> personalCategories = getPersonalCategories(user);
        // 그룹 카테고리
        List<GroupCategoryResponse> groupCategories = getGroupCategories(user);
        // 대기중인 초대장
        List<CategoryInvitationResponse> pendingInvitations = getCategoryInvitations(user, CategoryInvitationStatus.PENDING);

        return UserCategoryResponse.builder()
                .personalCategories(personalCategories)
                .groupCategories(groupCategories)
                .categoryInvitations(pendingInvitations)
                .build();
    }

    // 사용자의 일반 카테고리 가나다 순으로 조회
    @Override
    public List<PersonalCategoryResponse> getPersonalCategories(User user) {
        // 활성화되어있는 개인 카테고리 조회 (owner=user, type=PERSONAL, isDeleted=false)
        List<Category> personalCategories = categoryRepository.findByOwnerAndTypeAndDeletedIsFalse(user, CategoryType.PERSONAL);

        // 개인 카테고리를 가나다 순으로 정렬 후 DTO로 변환
        return personalCategories.stream()
                .sorted(Comparator.comparing(Category::getName)) // 이름(name) 기준으로 가나다 순 정렬
                .map(this::convertToPersonalCategory)
                .collect(Collectors.toList());
    }

    // 사용가 속한 그룹 카테고리 가나다 순으로 조회
    @Override
    public List<GroupCategoryResponse> getGroupCategories(User user) {
        // 활성화되어있는 그룹 카테고리 조회
        List<Category> groupCategories = findUserGroupCategories(user);

        // 멤버 수 정보를 미리 조회
        Map<Category, Long> categoryMemberCountMap = calculateMemberCounts(groupCategories);

        // 카테고리와 멤버 수 정보를 GroupCategoryResponse로 변환하여 반환 (가나다 순 정렬)
        return groupCategories.stream()
                .sorted(Comparator.comparing(Category::getName)) // 가나다 순 정렬
                .map(category -> convertToGroupCategory(user, category, categoryMemberCountMap.get(category)))
                .collect(Collectors.toList());
    }

    // 사용자가 받은 카테고리 초대장 조회
    @Override
    public List<CategoryInvitationResponse> getCategoryInvitations(User user, CategoryInvitationStatus status) {
        // 초대 상태에 따른 초대장 최신순으로 조회 (invitee=user, CategoryInvitationStatus=PENDING)
        List<CategoryInvitation> CategoryInvitations = categoryInvitationRepository.findAllByInviteeAndStatusOrderByCreatedAtDesc(user, status);

        return CategoryInvitations.stream()
                .map(this::convertToCategoryInvitation)
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 속한 그룹 카테고리 조회
     *
     * @param user 사용자
     * @return 사용자가 속한 그룹 카테고리 리스트
     */
    private List<Category> findUserGroupCategories(User user) {
        // 카테고리 멤버로 활성회되어있는 그룹 카테고리 조회 (user=user, leftDate=null)
        List<CategoryMember> categoryMembers = categoryMemberRepository.findAllByUserAndLeftDateIsNull(user);
        return categoryMembers.stream()
                .map(CategoryMember::getCategory)
                .collect(Collectors.toList());
    }

    /**
     * 그룹 카테고리들 멤버 수 조회
     *
     * @param groupCategories 그룹 카테고리 리스트
     * @return 각 카테고리별 멤버 수가 포함된 맵
     */
    private Map<Category, Long> calculateMemberCounts(List<Category> groupCategories) {
        // 모든 카테고리의 멤버 수를 한 번에 가져와서 Map으로 변환 (List<Category>=groupCategories, leftDate=null)
        List<CategoryMember> activeCategoryMembers = categoryMemberRepository.findAllByCategoryInAndLeftDateIsNull(groupCategories);
        return activeCategoryMembers.stream()
                .collect(Collectors.groupingBy(
                        CategoryMember::getCategory,  // 카테고리별로 그룹화
                        Collectors.counting()      // 각 카테고리의 멤버 수 계산
                ));
    }

    /**
     * 카테고리 상세 조회
     *
     * @param categoryId 카테고리 ID
     * @param email 조회한 유저 이메일
     * @return CategoryDetailDTO 객체
     */
    @Override
    public CategoryDetailResponse getCategoryDetail(Long categoryId, String email){
        Category category = findByCategoryId(categoryId);
        List<CategoryMemberResponse> categoryMemberResponseList = new ArrayList<>();
        // 그룹 멤버 조회
        if (category.getType().equals(CategoryType.GROUP)) {
            List<CategoryMember> categoryMembers = categoryMemberRepository.findAllByCategoryAndStatus(category, CategoryInvitationStatus.ACCEPTED);
            categoryMemberResponseList = categoryMembers.stream()
                    .sorted(Comparator.comparing(categoryMember -> categoryMember.getUser().getNickname())) // 닉네임으로 정렬
                    .map(categoryMember -> CategoryMemberResponse.builder()
                            .groupMemberId(categoryMember.getId())
                            .nickname(categoryMember.getUser().getNickname())
                            .profileImageUrl(categoryMember.getUser().getProfileImageUrl())
                            .build())
                    .collect(Collectors.toList());
        }

        // memberCount 계산
        int memberCount = categoryMemberResponseList.size();

        return CategoryDetailResponse.builder()
                .categoryId(categoryId)
                .name(category.getTitle())
                .ownerNickname(category.getHost().getNickname())
                .ownerStatus(category.getHost().getEmail().equals(email))
                .type(category.getType())
                .visibility(category.getVisibility())
                .members(categoryMemberResponseList)
                .memberCount(memberCount)
                .color(category.getColor())
                .build();
    }

    private Category findByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("카테고리를 찾을 수 없음: {}", categoryId);
                    return new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
                });
    }

    // 응답 생성
    /**
     * 주어진 카테고리를 PersonalCategoryResponse로 변환
     *
     * @param category 카테고리 엔티티
     * @return PersonalCategoryResponse 객체
     */
    private PersonalCategoryResponse convertToPersonalCategory(Category category) {
        return PersonalCategoryResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();
    }

    /**
     * 주어진 카테고리를 GroupCategoryResponse로 변환
     *
     * @param user 현재 사용자
     * @param category 카테고리 엔티티
     * @param totalMembers 해당 카테고리의 멤버 수
     * @return GroupCategoryResponse 객체
     */
    private GroupCategoryResponse convertToGroupCategory(User user, Category category, Long totalMembers) {
        // 현재 사용자가 owner인지 확인
        boolean ownerStatus = user.equals(category.getOwner());

        return GroupCategoryResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .totalMembers(Math.toIntExact(totalMembers))
                .ownerStatus(ownerStatus)
                .build();
    }

    /**
     * 초대장 정보를 CategoryInvitationResponse로 변환
     *
     * @param categoryInvitation 카테고리 초대장
     * @return CategoryInvitationResponse 객체
     */
    private CategoryInvitationResponse convertToCategoryInvitation(CategoryInvitation categoryInvitation) {

        return CategoryInvitationResponse.builder()
                .categoryInvitationId(categoryInvitation.getId())
                .categoryName(categoryInvitation.getCategory().getName())
                .ownerNickname(categoryInvitation.getInviter().getNickname())
                .build();
    }
}

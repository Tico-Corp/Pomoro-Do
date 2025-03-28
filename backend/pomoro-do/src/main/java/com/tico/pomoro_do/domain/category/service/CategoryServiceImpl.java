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

    /**
     * 카테고리 생성 및 그룹 카테고리 관련 멤버 처리 - 공개 API
     * 그룹 카테고리면,
     * 1.생성자는 관리자 멤버로 생성
     * 2.초대 멤버들에게 초대장 발송
     */

    @Override
    @Transactional
    public Long processCategoryCreation(Long userId, CategoryCreateRequest request) {
        // 1. 사용자 조회
        User owner = userService.findUserById(userId);

        // 2. 개인/그룹 카테고리 생성
        Category category = createCategory(
                owner, request.getStartDate(), request.getName(), request.getType(), request.getVisibility()
        );

        // 그룹 카테고리인 경우 멤버 처리
        if (CategoryType.GROUP.equals(request.getType())) {
            // 그룹 멤버 및 초대장 처리 - N+1 문제 개선 버전
            processGroupCategoryMembers(category, owner, request.getMemberIds());
        }

        log.info("카테고리 생성 완료: id={}, 유형={}, 이름={}, 소유자={}",
                category.getId(), category.getType(), category.getName(), owner.getId());

        return category.getId();
    }

    /**
     * 카테고리 엔티티를 생성하고 저장합니다.
     */
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
     * 그룹 카테고리 멤버 처리 및 초대장 발송
     * 1. 소유자(생성자)를 그룹 관리자 멤버로 추가
     * 2. 유효한 멤버 ID 필터링을 위해 팔로우 관계를 한 번에 조회
     * 3. 초대할 사용자 정보를 한 번에 조회
     * 4. 초대장 일괄 생성 및 저장
     *
     * @param category 생성된 카테고리
     * @param owner 카테고리 소유자
     * @param memberIds 초대할 멤버 ID 집합
     */
    private void processGroupCategoryMembers(Category category, User owner, Set<Long> memberIds) {
        // 1. 소유자를 카테고리 멤버로 추가
        createCategoryMember(category, owner, CategoryMemberRole.OWNER);
        log.debug("그룹 소유자 등록: 카테고리={}, 소유자={}", category.getId(), owner.getId());

        // 2. 멤버가 없으면 처리 종료
        // 그룹 카테고리를 생성할 때 추가 안 할 수 도 있음
        if (memberIds == null || memberIds.isEmpty()) {
            log.debug("초대할 멤버가 없음: 카테고리={}", category.getId());
            return;
        }

        // 소유자는 멤버에서 제외 (중복 방지)
        memberIds.remove(owner.getId());
        if (memberIds.isEmpty()) {
            return;
        }

        // 3. 팔로우 관계 일괄 조회 (N+1 문제 방지)
        Set<Long> followingIds = followService.getFollowingIdsByUser(owner.getId());

        // 4. 팔로우 중인 사용자만 필터링
        Set<Long> followedMemberIds = memberIds.stream()
                .filter(followingIds::contains)
                .collect(Collectors.toSet());

        // 팔로우 중인 사용자 없으면 처리 종료
        if (followedMemberIds.isEmpty()) {
            log.warn("초대 가능한 팔로우 사용자 없음: 카테고리={}", category.getId());
            return;
        }

        // 5. 유효한 사용자 정보 일괄 조회 (N+1 문제 방지)
        Map<Long, User> inviteeMap = userService.findUsersByIds(followedMemberIds);

        // 6. 초대장 일괄 생성
        List<CategoryInvitation> invitations = createCategoryInvitations(category, owner, followedMemberIds, inviteeMap);

        // 7. 초대장 일괄 저장 (단일 쿼리로 처리)
        if (!invitations.isEmpty()) {
            categoryInvitationRepository.saveAll(invitations);
            log.info("그룹 멤버 초대 완료: 카테고리={}, 초대 수={}",
                    category.getId(), invitations.size());
        }
    }

    /**
     * 카테고리 멤버를 추가합니다
     */
    @Override
    public void createCategoryMember(Category category, User user, CategoryMemberRole role) {
        // 중복 멤버 확인
        if (categoryMemberRepository.existsByCategoryAndUser(category, user)) {
            log.warn("이미 등록된 카테고리 멤버: 카테고리={}, 사용자={}", category.getId(), user.getId());
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

        log.debug("카테고리 멤버 등록 완료: categoryId={}, userId={}, role={}",
                category.getId(), member.getId(), role);
    }

    /**
     * 초대장 목록을 생성합니다
     */
    private List<CategoryInvitation> createCategoryInvitations(
            Category category, User owner, Set<Long> memberIds, Map<Long, User> userMap) {

        return memberIds.stream()
                .map(userMap::get)
                .filter(Objects::nonNull)
                .map(invitee -> CategoryInvitation.builder()
                        .category(category)
                        .inviter(owner)
                        .invitee(invitee)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 초대장을 생성합니다.
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
    public UserCategoryResponse getCategories(Long userId, CategoryType type) {
        // 사용자 조회
        User user = userService.findUserById(userId);

        // 타입 파라미터에 따라 적절한 카테고리 조회
        return type == null ? getAllCategories(user) : getCategoriesByType(user, type);
    }

    // 모든 종류의 카테고리 목록 조회 (개인/그룹/초대받은 카테고리)
    private UserCategoryResponse getAllCategories(User user) {
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

    // 유형별 카테고리 목록 조회 (개인/그룹 카테고리)
    private UserCategoryResponse getCategoriesByType(User user, CategoryType type) {

        List<PersonalCategoryResponse> personalCategories = Collections.emptyList();
        List<GroupCategoryResponse> groupCategories = Collections.emptyList();

        // 명시적으로 타입 체크
        if (type == CategoryType.PERSONAL) {
            personalCategories = getPersonalCategories(user);
        } else if (type == CategoryType.GROUP) {
            groupCategories = getGroupCategories(user);
        } else {
            throw new CustomException(ErrorCode.INVALID_CATEGORY_TYPE);
        }

        // 특정 타입만 조회할 때는 초대장 정보는 포함하지 않음
        List<CategoryInvitationResponse> emptyInvitations = Collections.emptyList();

        return UserCategoryResponse.builder()
                .personalCategories(personalCategories)
                .groupCategories(groupCategories)
                .categoryInvitations(emptyInvitations)
                .build();
    }


    /**
     *  사용자의 개인 카테고리 가나다 순으로 조회
     *
     * @param user 사용자
     * @return 사용자의 개인 카테고리를 포함하는 PersonalCategoryResponse 리스트
     */
    private List<PersonalCategoryResponse> getPersonalCategories(User user) {
        // 활성화되어있는 개인 카테고리 조회 (owner=user, type=PERSONAL, isDeleted=false)
        List<Category> personalCategories = categoryRepository.findByOwnerAndTypeAndDeletedIsFalse(user, CategoryType.PERSONAL);

        // 가나다 순으로 정렬 후 DTO로 변환
        return personalCategories.stream()
                .sorted(Comparator.comparing(Category::getName)) // 이름(name) 기준으로 가나다 순 정렬
                .map(this::convertToPersonalCategory)
                .collect(Collectors.toList());
    }

    /**
     * 사용자의 그룹 카테고리 가나다 순으로 조회
     *
     * @param user 사용자 ID
     * @return 그룹 카테고리 응답 리스트
     */
    private List<GroupCategoryResponse> getGroupCategories(User user) {
        // 사용자가 속한 그룹 카테고리 조회
        List<Category> groupCategories = findUserGroupCategories(user);

        // 멤버 수 정보를 미리 조회
        // 카테고리 ID 기반 멤버 수 맵 조회 (한 번의 쿼리로 처리)
        Map<Long, Long> categoryMemberCountMap = calculateMemberCounts(groupCategories);

        // 카테고리와 멤버 수 정보를 GroupCategoryResponse로 변환하여 반환 (가나다 순 정렬)
        return groupCategories.stream()
                .sorted(Comparator.comparing(Category::getName)) // 가나다 순 정렬
                .map(category -> convertToGroupCategory(
                        category,
                        categoryMemberCountMap.getOrDefault(category.getId(), 0L))
                )
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 속한 그룹 카테고리 조회
     *
     * @param user 사용자
     * @return 그룹 카테고리 목록
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
     * @param groupCategories 그룹 카테고리 목록
     * @return 카테고리 ID별 멤버 수 맵
     */
    private Map<Long, Long> calculateMemberCounts(List<Category> groupCategories) {
        // 모든 카테고리의 멤버 수를 한 번에 가져와서 Map으로 변환 (List<Category>=groupCategories, leftDate=null)
        List<CategoryMember> activeCategoryMembers =
                categoryMemberRepository.findAllByCategoryInAndLeftDateIsNull(groupCategories);
        return activeCategoryMembers.stream()
                .collect(Collectors.groupingBy(
                        categoryMember -> categoryMember.getCategory().getId(),  // 카테고리 ID 기준 그룹화
                        Collectors.counting()      // 각 카테고리의 멤버 수 계산
                ));
    }

    @Override
    public List<CategoryInvitationResponse> getCategoryInvitationsByStatus(Long userId, CategoryInvitationStatus status) {
        // 사용자 조회
        User user = userService.findUserById(userId);

        return getCategoryInvitations(user, status);
    }

    // 초대장 조회 - 상태별, 최신순
    private List<CategoryInvitationResponse> getCategoryInvitations(User user, CategoryInvitationStatus status) {
        // 초대 상태에 따른 초대장 최신순으로 조회 (ex, invitee=user, CategoryInvitationStatus=PENDING)
        List<CategoryInvitation> categoryInvitations = categoryInvitationRepository.findAllByInviteeAndStatusOrderByCreatedAtDesc(user, status);

        return categoryInvitations.stream()
                .map(this::convertToCategoryInvitation)
                .collect(Collectors.toList());
    }

    // 카테고리 상세 조회
    @Override
    public CategoryDetailResponse getCategoryDetail(Long categoryId, Long userId){
        // 카테고리 조회
        Category category = findByCategoryId(categoryId);

        List<CategoryMemberResponse> categoryMemberResponseList = new ArrayList<>();
        int totalMembers = 0;

        // 그룹 카테고리면 멤버 조회
        if (category.getType().equals(CategoryType.GROUP)) {
            // 해당 그룹의 활성화 되어있는 멤버들 가져오기
            List<CategoryMember> categoryMembers = categoryMemberRepository.findAllByCategoryAndLeftDateIsNull(category);
            // 카테고리 전체 멤버 수
            totalMembers = categoryMembers.size();

            categoryMemberResponseList = categoryMembers.stream()
                    .map(categoryMember -> CategoryMemberResponse.builder()
                            .groupMemberId(categoryMember.getId())
                            .nickname(categoryMember.getUser().getNickname())
                            .profileImageUrl(categoryMember.getUser().getProfileImageUrl())
                            .build())
                    .sorted(Comparator.comparing(CategoryMemberResponse::getNickname))
                    .collect(Collectors.toList());
        }

        return CategoryDetailResponse.builder()
                .categoryId(categoryId)
                .name(category.getName())
                .ownerNickname(category.getOwner().getNickname())
                .ownerFlag(category.getOwner().getId().equals(userId))
                .type(category.getType())
                .visibility(category.getVisibility())
                .members(categoryMemberResponseList)
                .totalMembers(totalMembers)
                .build();
    }

    private Category findByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("카테고리를 찾을 수 없습니다. ID: " + categoryId);
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
     * @param category 카테고리 엔티티
     * @param totalMembers 해당 카테고리의 멤버 수
     * @return GroupCategoryResponse 객체
     */
    private GroupCategoryResponse convertToGroupCategory(Category category, Long totalMembers) {

        return GroupCategoryResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .totalMembers(Math.toIntExact(totalMembers))
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
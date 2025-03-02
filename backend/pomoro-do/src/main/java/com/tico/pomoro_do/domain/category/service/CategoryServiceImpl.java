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
import com.tico.pomoro_do.global.common.util.ValidationUtils;
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

    // 개인/그룹 생성할 때 그룹 멤버까지 생성
    @Override
    @Transactional
    public void createCategory(Long userId, CategoryCreateRequest categoryCreateRequest){

        User owner = userService.findUserById(userId);

        // 개인/그룹 카테고리 생성
        Category category = createNewCategory(
                owner,
                categoryCreateRequest.getStartDate(),
                categoryCreateRequest.getCategoryName(),
                categoryCreateRequest.getCategoryType(),
                categoryCreateRequest.getCategoryVisibility()
        );

        // 그룹 카테고리면 그룹 멤버 생성
        if (categoryCreateRequest.getCategoryType() == CategoryType.GROUP) {
            // 그룹 멤버를 받아왔는 지 검사
            ValidationUtils.validateGroupMembers(categoryCreateRequest.getGroupMemberIds());
            // GroupMember 생성
            createCategoryMembers(category, owner, categoryCreateRequest.getGroupMemberIds(), categoryCreateRequest.getStartDate());
        }
    }

    // 새로운 카테고리 생성
    @Override
    public Category createNewCategory(User owner, LocalDate startDate, String name, CategoryType type, CategoryVisibility visibility) {
        Category category = Category.builder()
                .owner(owner)
                .name(name)
                .type(type)
                .startDate(startDate)
                .visibility(visibility)
                .build();
        return categoryRepository.save(category);
    }


    /**
     * 그룹 카테고리의 멤버들을 생성하여 저장
     *
     * @param category 초대한 카테고리 객체
     * @param owner 카테고리 관리자 유저 객체
     * @param memberIds 그룹 카테고리에 포함될 멤버들의 ID Set
     */
    private void createCategoryMembers(Category category, User owner, Set<Long> memberIds, LocalDate joinedDate) {

        // 카테고리 멤버 초대
        // 팔로우한 멤버만 그룹에 추가 가능
        for (Long memberId : memberIds) {
            // 관리자가 팔로우하지 않은 사용자일 경우 예외 발생
            if (!followService.isFollowing(owner.getId(), memberId)) {
                log.warn("팔로우하지 않은 사용자 [{}] 추가 시도", memberId);
                throw new CustomException(ErrorCode.CATEGORY_MEMBER_NOT_FOLLOWED);
            }
            // 초대할 멤버
            User invitee = userService.findUserById(memberId);
            // 초대장 보내기
            createCategoryInvitation(category, owner, invitee);
        }

        // 카테고리 관리자 생성
        createCategoryMember(category, owner, CategoryMemberRole.OWNER, joinedDate);
    }

    // 그룹 카테고리 멤버 생성
    @Override
    public void createCategoryMember(Category category, User member, CategoryMemberRole role, LocalDate joinedDate){
        CategoryMember categoryMember = CategoryMember.builder()
                .category(category)
                .user(member)
                .role(role)
                .joinedDate(joinedDate)
                .build();

        categoryMemberRepository.save(categoryMember);
    }

    /**
     * 그룹 카테고리 초대장 생성 (보내기)
     *
     * @param category 초대한 카테고리
     * @param inviter 초대한 유저
     * @param invitee 초대받은 유저
     */
    private void createCategoryInvitation(Category category, User inviter, User invitee) {
        CategoryInvitation categoryInvitation = CategoryInvitation.builder()
                .category(category)
                .inviter(inviter)
                .invitee(invitee)
                .build();

        categoryInvitationRepository.save(categoryInvitation);
    }

    // 일반/그룹/초대받은 카테고리 조회
    @Override
    public CategoryResponse getCategories(Long userId) {

        // 사용자 조회
        User user = userService.findUserById(userId);

        // 개인 카테고리
        List<PersonalCategoryResponse> personalCategories = getPersonalCategories(user);
        // 그룹 카테고리
        List<GroupCategoryResponse> groupCategories = getGroupCategories(user);
        // 대기중인 초대장
        List<CategoryInvitationResponse> pendingInvitations = getCategoryInvitations(user, CategoryInvitationStatus.PENDING);

        return CategoryResponse.builder()
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

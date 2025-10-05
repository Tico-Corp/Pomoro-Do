package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.request.CategoryDeleteRequest;
import com.tico.pomoro_do.domain.category.dto.request.CategoryUpdateRequest;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.enums.*;
import com.tico.pomoro_do.domain.category.repository.CategoryRepository;
import com.tico.pomoro_do.domain.category.validator.CategoryValidator;
import com.tico.pomoro_do.domain.user.entity.User;
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
    private final CategoryMemberService categoryMemberService;
    private final CategoryInvitationService categoryInvitationService;
    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    /**
     * 카테고리 생성 및 그룹 카테고리 관련 멤버 처리
     * 그룹 카테고리면,
     * 1. 생성자는 그룹의 OWNER 멤버로 등록
     * 2. 초대 대상자에게 초대장 발송
     */
    @Override
    @Transactional
    public CategoryInfoResponse processCategoryCreation(Long userId, CategoryCreateRequest request) {
        // 1. 사용자 조회
        User owner = userService.findUserById(userId);

        // 2. 개인/그룹 카테고리 생성
        Category category = createCategory(
                owner, request.getStartDate(), request.getName(), request.getType(), request.getVisibility()
        );

        // 3. 그룹 카테고리일 경우 멤버 및 초대 처리
        if (CategoryType.GROUP.equals(request.getType())) {
            // 그룹 멤버 및 초대장 처리 - N+1 문제 개선 버전
            initializeGroupMembers(category, owner, request.getMemberIds());
        }

        log.info("카테고리 생성 완료: categoryId={}, 유형={}, 이름={}, 소유자={}",
                category.getId(), category.getType(), category.getName(), owner.getId());

        return CategoryInfoResponse.of(category, 1);
    }

    /**
     * 카테고리 생성
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
     * 그룹 카테고리 초기 멤버 설정 (V1에서는 생성 시에만 설정 가능)
     * 1. 소유자를 그룹 OWNER 멤버로 등록합니다.
     * 2. 소유자를 제외한 memberIds 중 팔로우 관계 + 중복 멤버 제외 후 초대장을 발송합니다.
     *
     * @param category 그룹 카테고리
     * @param owner 그룹 생성자 (OWNER)
     * @param memberIds 초대할 멤버 ID 목록 (팔로우 관계 전제)
     */
    private void initializeGroupMembers(Category category, User owner, Set<Long> memberIds) {
        // 1. 소유자를 그룹 관리자 멤버로 등록
        categoryMemberService.createCategoryMember(category, owner, CategoryMemberRole.OWNER);
        log.debug("그룹 소유자 등록 완료: categoryId={}, ownerId={}", category.getId(), owner.getId());

        // 2. 초대할 멤버가 존재하면 초대 처리
        if (memberIds != null && !memberIds.isEmpty()) {
            categoryInvitationService.inviteMembers(category, owner, memberIds);
        }

    }

    /**
     * 사용자의 카테고리 조회
     * - 개인 / 그룹 / 초대 받은 카테고리 포함 여부는 type 파라미터로 제어됩니다.
     */
    @Override
    public UserCategoryResponse getCategories(Long userId, CategoryType type) {
        // 사용자 조회
        User user = userService.findUserById(userId);

        // 타입 파라미터에 따라 적절한 카테고리 조회
        return type == null ? getAllCategories(user) : getCategoriesByType(user, type);
    }

    /**
     * 전체 카테고리(개인/그룹/초대받은) 조회
     */
    private UserCategoryResponse getAllCategories(User user) {
        // 개인 카테고리
        List<PersonalCategoryResponse> personalCategories = getPersonalCategories(user);
        // 그룹 카테고리
        List<GroupCategoryResponse> groupCategories = getGroupCategories(user);
        // 대기중인 초대장
        List<CategoryInvitationResponse> pendingInvitations = categoryInvitationService.findInvitationsByStatus(user, CategoryInvitationStatus.PENDING);

        return UserCategoryResponse.builder()
                .personalCategories(personalCategories)
                .groupCategories(groupCategories)
                .categoryInvitations(pendingInvitations)
                .build();
    }

    /**
     * 유형별 카테고리 조회 (개인/그룹 카테고리)
     */
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
     *  사용자 개인 카테고리 조회 (가나다 순)
     *
     * @param user 사용자
     * @return 개인 카테고리 응답 리스트
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
     * 사사용자 그룹 카테고리 조회 (가나다 순)
     *
     * @param user 사용자
     * @return 그룹 카테고리 응답 리스트
     */
    private List<GroupCategoryResponse> getGroupCategories(User user) {
        // 사용자가 속한 그룹 카테고리 조회
        List<Category> groupCategories = categoryMemberService.getActiveCategoriesByUser(user);

        // 멤버 수 정보를 미리 조회
        // 카테고리 ID 기반 멤버 수 맵 조회 (한 번의 쿼리로 처리)
        Map<Long, Long> categoryMemberCountMap = categoryMemberService.getActiveMemberCounts(groupCategories);

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
     * 카테고리 상세 조회 (멤버 포함)
     */
    @Override
    public CategoryDetailResponse getCategoryDetail(Long categoryId, Long userId) {
        // 1. 카테고리 조회
        Category category = categoryValidator.validateExists(categoryId);

        // 2. 멤버 응답 DTO 리스트 및 멤버 수 초기화
        List<CategoryMemberResponse> categoryMemberResponseList = new ArrayList<>();
        int totalMembers = 0;

        // 3. 그룹 카테고리인 경우 멤버 정보 조회
        if (category.getType().equals(CategoryType.GROUP)) {
            // 카테고리 멤버 정보 목록
            categoryMemberResponseList = categoryMemberService.getMemberResponses(category);
            // 카테고리 전체 멤버 수
            totalMembers = categoryMemberResponseList.size();
        }

        // 4. 카테고리 상세 응답 생성
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

    // 응답 생성
    /**
     * 주어진 카테고리를 PersonalCategoryResponse로 변환
     *
     * @param category 카테고리
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
     * @param category 카테고리
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
     * 카테고리 수정
     * - 소유자만 수정할 수 있습니다.
     * - 일반 카테고리는 이름과 공개 범위를 수정할 수 있습니다.
     * - 그룹 카테고리는 이름만 수정 가능하며, 공개 범위는 항상 GROUP으로 고정됩니다.
     *
     * @param userId     수정 요청 사용자 ID
     * @param categoryId 수정 대상 카테고리 ID
     * @param request    수정 정보
     * @throws CustomException 수정 권한 없음, 삭제됨 등 예외
     */
    @Transactional
    @Override
    public CategoryInfoResponse updateCategory(Long categoryId, Long userId, CategoryUpdateRequest request) {
        // 1. 카테고리 조회
        Category category = categoryValidator.validateExists(categoryId);

        // 2. 삭제 여부 확인
        if (category.isDeleted()) {
            log.warn("삭제된 카테고리 수정 시도: categoryId={}", categoryId);
            throw new CustomException(ErrorCode.CATEGORY_ALREADY_DELETED);
        }

        // 3. 소유자 권한 검증
        if (!category.isOwner(userId)) {
            log.warn("소유자가 아닌 사용자의 수정 시도: userId={}, categoryId={}", userId, categoryId);
            throw new CustomException(ErrorCode.CATEGORY_UPDATE_FORBIDDEN);
        }

        // 4. 도메인 정책에 따른 수정 분기
        if (category.isGroup()) {
            // 그룹 카테고리는 name만 수정 가능, visibility 수정 시 예외 발생
            if (request.getVisibility() != null && request.getVisibility() != CategoryVisibility.GROUP) {
                log.warn("그룹 카테고리의 공개 설정 변경 시도 차단: categoryId={}, visibility={}", categoryId, request.getVisibility());
                throw new CustomException(ErrorCode.CATEGORY_VISIBILITY_UPDATE_FORBIDDEN);
            }
            category.updateName(request.getName());
        } else {
            category.update(request.getName(), request.getVisibility());
        }

        int totalMembers = (category.getType() == CategoryType.GROUP)
                ? categoryMemberService.countActiveMembers(category.getId())
                : 1;

        return CategoryInfoResponse.of(category, totalMembers);
    }

    /**
     * 카테고리 삭제
     * - 소유자만 삭제할 수 있으며, 삭제 정책을 기반으로 논리 삭제 처리됩니다.
     * - 그룹 카테고리인 경우, 그룹 멤버들을 탈퇴 처리하고 PENDING 상태 초대장을 모두 제거합니다.
     *
     * @param userId 삭제 요청 사용자 ID
     * @param categoryId 삭제 대상 카테고리 ID
     * @param request 삭제 정책 정보 (CategoryDeletionOption)
     * @throws CustomException 삭제 권한 없음, 이미 삭제됨 등의 예외 발생 가능
     */
    @Override
    @Transactional
    public void deleteCategory(Long userId, Long categoryId, CategoryDeleteRequest request) {
        log.info("카테고리 삭제 요청: userId={}, categoryId={}, deletionOption={}",
                userId, categoryId, request.getDeletionOption());

        // 1. 카테고리 조회
        Category category = categoryValidator.validateExists(categoryId);

        // 2. 삭제 여부 확인
        if (category.isDeleted()) {
            log.warn("이미 삭제된 카테고리에 대한 삭제 요청: categoryId={}", categoryId);
            throw new CustomException(ErrorCode.CATEGORY_ALREADY_DELETED);
        }

        // 3. 소유자인지 확인
        if (!category.isOwner(userId)) {
            log.warn("카테고리 소유자가 아닌 사용자의 삭제 시도: userId={}, categoryId={}", userId, categoryId);
            throw new CustomException(ErrorCode.CATEGORY_DELETE_FORBIDDEN);
        }

        // 4. 논리 삭제 (삭제 옵션 포함)
        category.delete(request.getDeletionOption());  // endDate, 삭제옵션, deleted = true
        log.info("카테고리 논리 삭제 완료: categoryId={}, option={}", categoryId, request.getDeletionOption());

        // 5. 그룹 카테고리인 경우 추가 처리
        if (category.isGroup()) {
            log.debug("그룹 카테고리 삭제 후 멤버 탈퇴 및 초대장 제거 처리 시작: categoryId={}", categoryId);

            // 5-1. 활성화된 멤버 모두 탈퇴 처리
            categoryMemberService.leaveAllActiveMembers(category, request.getDeletionOption());
            log.info("그룹 멤버 탈퇴 처리 완료: categoryId={}", categoryId);

            // 5-2. 응답 대기중인 초대장 물리 삭제 (PENDING 상태)
            categoryInvitationService.deleteAllPendingInvitations(category);
            log.info("PENDING 초대장 삭제 완료: categoryId={}", categoryId);
        }

        log.info("카테고리 삭제 전체 프로세스 완료: categoryId={}", categoryId);
    }
}
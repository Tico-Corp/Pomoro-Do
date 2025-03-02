package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreationDTO;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.category.repository.CategoryRepository;
import com.tico.pomoro_do.domain.category.repository.GroupMemberRepository;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.FollowService;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.global.exception.ErrorCode;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.common.util.DateUtils;
import com.tico.pomoro_do.global.common.util.ValidationUtils;
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

    private final CategoryRepository categoryRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserService userService;
    private final FollowService followService;

    /**
     * 카테고리 생성
     *
     * @param hostEmail 카테고리를 생성하는 호스트 유저의 이메일
     * @param categoryCreationDTO 생성할 카테고리의 정보가 담긴 DTO
     */
    @Override
    @Transactional
    public void createCategory(String hostEmail, CategoryCreationDTO categoryCreationDTO){

        User host = userService.findUserByEmail(hostEmail);

        // 일반/그룹 카테고리 생성
        Category category = createNewCategory(
                host,
                categoryCreationDTO.getDate(),
                categoryCreationDTO.getTitle(),
                categoryCreationDTO.getColor(),
                categoryCreationDTO.getVisibility(),
                categoryCreationDTO.getType()
        );

        // 그룹 카테고리면 그룹 멤버 생성 로직
        if (categoryCreationDTO.getType() == CategoryType.GROUP) {
            // 그룹 멤버를 받아왔는 지 검사
            ValidationUtils.validateGroupMembers(categoryCreationDTO.getMembers());
            // GroupMember 생성
            createGroupMembers(category, host, categoryCreationDTO.getMembers());
        }
    }

    /**
     * 새로운 카테고리 객체를 생성하고 저장
     *
     * @param host 카테고리를 생성하는 호스트 유저 객체
     * @param title 카테고리의 제목
     * @param color 카테고리의 색상
     * @param visibility 카테고리의 공개 설정
     * @param type 카테고리의 유형 (일반/그룹)
     * @return 저장된 카테고리 객체
     */
    @Override
    public Category createNewCategory(User host, LocalDate date, String title, String color, CategoryVisibility visibility, CategoryType type) {
        Category category = Category.builder()
                .host(host)
                .date(date)
                .title(title)
                .color(color)
                .visibility(visibility)
                .type(type)
                .build();
        return categoryRepository.save(category);
    }


    /**
     * 그룹 카테고리의 멤버들을 생성하여 저장
     *
     * @param category 생성된 카테고리 객체
     * @param host 카테고리를 생성한 호스트 유저 객체
     * @param memberIds 그룹에 포함될 멤버들의 ID Set
     */
    private void createGroupMembers(Category category, User host, Set<Long> memberIds) {
        // 호스트 멤버 생성
        createGroupMember(category, host, CategoryInvitationStatus.ACCEPTED, CategoryMemberRole.HOST);

        // 팔로우한 멤버만 그룹에 추가 가능
        for (Long memberId : memberIds) {
            // 호스트가 팔로우하지 않은 사용자일 경우 예외 발생
            if (!followService.isFollowing(host.getId(), memberId)) {
                log.warn("팔로우하지 않은 사용자 [{}] 추가 시도", memberId);
                throw new CustomException(ErrorCode.CATEGORY_MEMBER_NOT_FOLLOWED);
            }

            User member = userService.findUserById(memberId);
            createGroupMember(category, member, CategoryInvitationStatus.INVITED, CategoryMemberRole.MEMBER);
        }
    }

    /**
     * 단일 그룹 멤버를 생성하여 저장
     *
     * @param category 생성된 카테고리 객체
     * @param member 그룹에 포함될 유저 객체
     * @param status 그룹 초대 상태 (ACCEPTED, INVITED 등)
     * @param role 그룹 내 유저의 역할 (HOST, MEMBER 등)
     */
    @Override
    public void createGroupMember(Category category, User member, CategoryInvitationStatus status, CategoryMemberRole role){
        CategoryMember categoryMember = CategoryMember.builder()
                .category(category)
                .user(member)
                .status(status)
                .role(role)
                .build();

        groupMemberRepository.save(categoryMember);
    }

    /**
     * 주어진 사용자 이름을 기반으로 사용자의 일반, 그룹, 초대받은 카테고리를 조회
     *
     * @param email 사용자의 이메일
     * @return 사용자에 해당하는 일반 카테고리, 그룹 카테고리, 초대받은 그룹 카테고리를 포함하는 CategoryDTO 객체
     */
    @Override
    public CategoryDTO getCategories(String email) {
        // 사용자 조회
        User user = userService.findUserByEmail(email);

        // 일반 카테고리
        List<GeneralCategoryDTO> generalCategories = getGeneralCategories(user);
        // ACCEPTED 상태인 그룹 카테고리
        List<GroupCategoryDTO> groupCategories = getGroupCategories(user);
        // INVITED 상태인 그룹 카테고리
        List<InvitedGroupDTO> invitedGroupCategories = getInvitedGroupCategories(user);

        return CategoryDTO.builder()
                .generalCategories(generalCategories)
                .groupCategories(groupCategories)
                .invitedGroupCategories(invitedGroupCategories)
                .build();
    }

    /**
     * 주어진 사용자를 호스트로 하는 일반 카테고리 조회
     *
     * @param host 사용자
     * @return 사용자의 일반 카테고리를 포함하는 GeneralCategoryDTO 리스트
     */
    @Override
    public List<GeneralCategoryDTO> getGeneralCategories(User host) {
        // DB에서 한번에 해당 날짜의 일반 카테고리를 조회
        List<Category> generalCategories = categoryRepository.findAllByHostAndTypeAndDate(host, CategoryType.GENERAL, DateUtils.getCurrentDate());

        // 일반 카테고리를 가나다 순으로 정렬 후 DTO로 변환
        return generalCategories.stream()
                .sorted(Comparator.comparing(Category::getTitle)) // 제목(title) 기준으로 가나다 순 정렬
                .map(this::convertToGeneralCategory)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 사용자를 기반으로 사용자가 속한 그룹 카테고리 조회
     *
     * @param user 사용자
     * @return 사용자가 속한 그룹 카테고리를 포함하는 GroupCategoryDTO 리스트
     */
    @Override
    public List<GroupCategoryDTO> getGroupCategories(User user) {
        // 해당 날짜의 사용자가 속한 그룹 카테고리들 가져오기
        List<Category> groupCategories = findUserGroupCategoriesByDate(user, DateUtils.getCurrentDate());

        // 멤버 수 정보를 미리 조회
        Map<Category, Long> categoryMemberCountMap = calculateMemberCounts(groupCategories);

        // 카테고리와 멤버 수 정보를 GroupCategoryDTO로 변환하여 반환 (가나다 순 정렬)
        return groupCategories.stream()
                .sorted(Comparator.comparing(Category::getTitle)) // 가나다 순 정렬
                .map(category -> convertToGroupCategory(category, categoryMemberCountMap.get(category)))
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 초대받은 그룹 카테고리 조회
     *
     * @param user 사용자
     * @return 사용자가 초대받은 그룹 카테고리를 포함하는 InvitedGroupDTO 리스트
     */
    @Override
    public List<InvitedGroupDTO> getInvitedGroupCategories(User user) {
        // 초대받은 그룹 카테고리를 최신순으로 가져옴
        List<CategoryMember> invitedGroups = groupMemberRepository.findByUserAndStatusOrderByCreatedAtDesc(user, CategoryInvitationStatus.INVITED);

        return invitedGroups.stream()
                .map(this::convertToInvitedGroup)
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 속한 그룹 카테고리 조회
     *
     * @param user 사용자
     * @param targetDate 조회할 날짜
     * @return 사용자가 속한 그룹 카테고리 리스트
     */
    private List<Category> findUserGroupCategoriesByDate(User user, LocalDate targetDate) {
        List<CategoryMember> userAcceptedGroups = groupMemberRepository.findAllByUserAndStatusAndCategory_Date(user, CategoryInvitationStatus.ACCEPTED, targetDate);
        return userAcceptedGroups.stream()
                .map(CategoryMember::getCategory)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 그룹 카테고리들의 멤버 수를 조회
     *
     * @param groupCategories 그룹 카테고리 리스트
     * @return 각 카테고리별 멤버 수가 포함된 맵
     */
    private Map<Category, Long> calculateMemberCounts(List<Category> groupCategories) {
        // 모든 카테고리의 멤버 수를 한 번에 가져와서 Map으로 변환
        List<CategoryMember> acceptedMembers = groupMemberRepository.findByCategoryInAndStatus(groupCategories, CategoryInvitationStatus.ACCEPTED);
        return acceptedMembers.stream()
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
    public CategoryDetailDTO getCategoryDetail(Long categoryId, String email){
        Category category = findByCategoryId(categoryId);
        List<GroupMemberDTO> groupMemberDTOList = new ArrayList<>();
        // 그룹 멤버 조회
        if (category.getType().equals(CategoryType.GROUP)) {
            List<CategoryMember> categoryMembers = groupMemberRepository.findAllByCategoryAndStatus(category, CategoryInvitationStatus.ACCEPTED);
            groupMemberDTOList = categoryMembers.stream()
                    .sorted(Comparator.comparing(categoryMember -> categoryMember.getUser().getNickname())) // 닉네임으로 정렬
                    .map(categoryMember -> GroupMemberDTO.builder()
                            .groupMemberId(categoryMember.getId())
                            .nickname(categoryMember.getUser().getNickname())
                            .profileImageUrl(categoryMember.getUser().getProfileImageUrl())
                            .build())
                    .collect(Collectors.toList());
        }

        // memberCount 계산
        int memberCount = groupMemberDTOList.size();

        return CategoryDetailDTO.builder()
                .categoryId(categoryId)
                .title(category.getTitle())
                .hostNickname(category.getHost().getNickname())
                .hostStatus(category.getHost().getEmail().equals(email))
                .type(category.getType())
                .visibility(category.getVisibility())
                .members(groupMemberDTOList)
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
     * 주어진 카테고리를 GeneralCategoryDTO로 변환
     *
     * @param category 카테고리 엔티티
     * @return GeneralCategoryDTO 객체
     */
    private GeneralCategoryDTO convertToGeneralCategory(Category category) {
        return GeneralCategoryDTO.builder()
                .categoryId(category.getId())
                .title(category.getTitle())
                .color(category.getColor())
                .build();
    }

    /**
     * 주어진 카테고리를 GroupCategoryDTO로 변환
     *
     * @param category 카테고리 엔티티
     * @param memberCount 해당 카테고리의 멤버 수
     * @return GroupCategoryDTO 객체
     */
    private GroupCategoryDTO convertToGroupCategory(Category category, Long memberCount) {

        return GroupCategoryDTO.builder()
                .categoryId(category.getId())
                .title(category.getTitle())
                .color(category.getColor())
                .memberCount(Math.toIntExact(memberCount))
                .build();
    }

    /**
     * 초대받은 그룹 정보를 InvitedGroupDTO로 변환
     *
     * @param categoryMember 그룹 멤버 엔티티
     * @return InvitedGroupDTO 객체
     */
    private InvitedGroupDTO convertToInvitedGroup(CategoryMember categoryMember) {
        Category category = categoryMember.getCategory();
        return InvitedGroupDTO.builder()
                .groupMemberId(categoryMember.getId())
                .hostNickname(category.getHost().getNickname())
                .title(category.getTitle())
                .build();
    }

    /**
     * 해당 날짜에 속하는 모든 카테고리 조회
     *
     * @param targetDate 조회할 날짜
     * @return 해당 날짜에 속하는 모든 카테고리 리스트
     */
    @Override
    public List<Category> findByDate(LocalDate targetDate) {
        // DB에서 한번에 해당 날짜의 일반 카테고리를 조회
        return categoryRepository.findByDate(targetDate);
    }

    /**
     * 해당 카테고리에 속하는 모든 승인된 그룹 멤버 조회
     *
     * @param category 조회할 카테고리
     * @return 해당 카테고리에 속하는 모든 승인된 그룹 멤버 리스트
     */
    @Override
    public List<CategoryMember> findAcceptedMembersByCategory(Category category) {
        return groupMemberRepository.findAllByCategoryAndStatus(category, CategoryInvitationStatus.ACCEPTED);
    }

}

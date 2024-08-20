package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryDTO;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.GroupMember;
import com.tico.pomoro_do.domain.category.repository.CategoryRepository;
import com.tico.pomoro_do.domain.category.repository.GroupMemberRepository;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.FollowService;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.enums.CategoryType;
import com.tico.pomoro_do.global.enums.CategoryVisibility;
import com.tico.pomoro_do.global.enums.GroupInviteStatus;
import com.tico.pomoro_do.global.enums.GroupRole;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserService userService;
    private final FollowService followService;

    /**
     * 카테고리 생성
     *
     * @param hostName 카테고리를 생성하는 호스트 유저의 이름
     * @param categoryDTO 생성할 카테고리의 정보가 담긴 DTO
     */
    @Override
    @Transactional
    public void createCategory(String hostName, CategoryDTO categoryDTO){

        User host = userService.findByUsername(hostName);

        // 일반/그룹 카테고리 생성
        Category category = createNewCategory(
                host,
                categoryDTO.getTitle(),
                categoryDTO.getColor(),
                categoryDTO.getVisibility(),
                categoryDTO.getType()
        );

        // 그룹 카테고리면 그룹 멤버 생성 로직
        if (categoryDTO.getType() == CategoryType.GROUP) {
            // 그룹 멤버를 받아왔는 지 검사
            ValidationUtils.validateGroupMembers(categoryDTO.getMembers());
            // GroupMember 생성
            createGroupMembers(category, host, categoryDTO.getMembers());
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
    public Category createNewCategory(User host, String title, String color, CategoryVisibility visibility, CategoryType type) {
        Category category = Category.builder()
                .host(host)
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
        createSingleGroupMember(category, host, GroupInviteStatus.ACCEPTED, GroupRole.HOST);

        // 팔로우한 멤버만 그룹에 추가 가능
        for (Long memberId : memberIds) {
            // 호스트가 팔로우하지 않은 사용자일 경우 예외 발생
            if (!followService.isFollowedByUser(host.getId(), memberId)) {
                log.warn("팔로우하지 않은 사용자 [{}] 추가 시도", memberId);
                throw new CustomException(ErrorCode.CATEGORY_MEMBER_NOT_FOLLOWED);
            }

            User member = userService.findByUserId(memberId);
            createSingleGroupMember(category, member, GroupInviteStatus.INVITED, GroupRole.MEMBER);
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
    private void createSingleGroupMember(Category category, User member, GroupInviteStatus status, GroupRole role){
        GroupMember groupMember = GroupMember.builder()
                .category(category)
                .user(member)
                .status(status)
                .role(role)
                .build();

        groupMemberRepository.save(groupMember);
    }

}

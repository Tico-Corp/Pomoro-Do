package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.response.CategoryMemberResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
import com.tico.pomoro_do.domain.category.repository.CategoryMemberRepository;
import com.tico.pomoro_do.domain.user.entity.User;
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
                        .groupMemberId(member.getId())
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
}
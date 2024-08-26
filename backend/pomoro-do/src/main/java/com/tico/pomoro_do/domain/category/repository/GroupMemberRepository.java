package com.tico.pomoro_do.domain.category.repository;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.GroupMember;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.GroupInvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    // 나의 그룹 카테고리 조회
    List<GroupMember> findAllByUserAndStatus(User user, GroupInvitationStatus status);
    // 초대받은 그룹 카테고리를 초대받은 시간 기준으로 정렬해서 가져오기
    List<GroupMember> findByUserAndStatusOrderByCreatedAtDesc(User user, GroupInvitationStatus status);

    // 동일한 카테고리의 ACCEPTED 상태의 멤버만 조회
    List<GroupMember> findByCategoryInAndStatus(List<Category> categories, GroupInvitationStatus status);

    List<GroupMember> findAllByCategoryAndStatus(Category category, GroupInvitationStatus status);

    // 초대된 그룹원 수 (카테고리와 상태로 멤버 수를 카운트)
    int countByCategoryAndStatus(Category category, GroupInvitationStatus status);

}

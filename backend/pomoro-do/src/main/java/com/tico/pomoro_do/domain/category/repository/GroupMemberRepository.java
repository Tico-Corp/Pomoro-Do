package com.tico.pomoro_do.domain.category.repository;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.GroupMember;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.GroupInvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    // 사용자가 속한 모든 그룹 카테고리 (승낙된 것과 초대받은 것 모두)
    List<GroupMember> findAllByUser(User user);

    // 나의 그룹 카테고리 조회
    List<GroupMember> findAllByUserAndStatus(User user, GroupInvitationStatus status);

    // 해당 날짜의 그룹 카테고리 가져오기
    List<GroupMember> findAllByUserAndStatusAndCategory_Date(User user, GroupInvitationStatus status, LocalDate date);

    // 그룹 카테고리를 최신순으로 정렬해서 가져오기
    List<GroupMember> findByUserAndStatusOrderByCreatedAtDesc(User user, GroupInvitationStatus status);

    // 동일한 카테고리의 ACCEPTED 상태의 멤버만 조회
    List<GroupMember> findByCategoryInAndStatus(List<Category> categories, GroupInvitationStatus status);

    List<GroupMember> findAllByCategoryAndStatus(Category category, GroupInvitationStatus status);

}

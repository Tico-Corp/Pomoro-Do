package com.tico.pomoro_do.domain.category.repository;

import com.tico.pomoro_do.domain.category.entity.GroupMember;
import com.tico.pomoro_do.global.enums.GroupInviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    // 나의 그룹 카테고리 조회 -> 생성(승낙) 시간 순으로 정렬
    List<GroupMember> findByUserIdAndStatusOrderByUpdatedAtAsc(Long userId, GroupInviteStatus status);

    // 초대된 그룹원 수
    int countByCategoryIdAndStatus(Long categoryId, GroupInviteStatus status);

}

package com.tico.pomoro_do.domain.category.repository;

import com.tico.pomoro_do.domain.category.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}

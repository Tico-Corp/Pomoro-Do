package com.tico.pomoro_do.domain.category.repository;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CategoryMemberRepository extends JpaRepository<CategoryMember, Long> {

    // 활성회되어있는 카테고리 멤버 조회 (user=user, leftDate=null)
    List<CategoryMember> findAllByUserAndLeftDateIsNull(User user);

    // 카테고리 멤버 조회 (category=category, leftDate=null)
    List<CategoryMember> findAllByCategoryInAndLeftDateIsNull(List<Category> categories);


}

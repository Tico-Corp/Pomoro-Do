package com.tico.pomoro_do.domain.category.repository;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryMemberRepository extends JpaRepository<CategoryMember, Long> {

    // 활성회되어있는 카테고리 멤버 조회 (user=user, leftDate=null)
    List<CategoryMember> findAllByUserAndLeftDateIsNull(User user);

    // 카테고리 멤버 조회 (category=category, leftDate=null)
    List<CategoryMember> findAllByCategoryInAndLeftDateIsNull(List<Category> categories);

    /**
     * 카테고리와 사용자로 멤버십 존재 여부 확인
     *
     * @param category 카테고리
     * @param user 사용자
     * @return 멤버십 존재 여부
     */
    boolean existsByCategoryAndUser(Category category, User user);

    /**
     * 카테고리 ID와 사용자 ID로 멤버십 존재 여부 확인
     *
     * @param categoryId 카테고리 ID
     * @param userId 사용자 ID
     * @return 멤버십 존재 여부
     */
    boolean existsByCategoryIdAndUserId(Long categoryId, Long userId);

    /**
     * 카테고리 ID와 사용자 ID로 멤버십 조회
     *
     * @param categoryId 카테고리 ID
     * @param userId 사용자 ID
     * @return 멤버십 Optional
     */
    Optional<CategoryMember> findByCategoryIdAndUserId(Long categoryId, Long userId);

    /**
     * 카테고리 ID로 해당 카테고리의 모든 멤버십 조회
     *
     * @param categoryId 카테고리 ID
     * @return 멤버십 목록
     */
    List<CategoryMember> findByCategoryId(Long categoryId);

    /**
     * 사용자 ID로 해당 사용자의 모든 멤버십 조회
     *
     * @param userId 사용자 ID
     * @return 멤버십 목록
     */
    List<CategoryMember> findByUserId(Long userId);
}


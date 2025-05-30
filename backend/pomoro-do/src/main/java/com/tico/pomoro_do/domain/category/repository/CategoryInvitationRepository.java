package com.tico.pomoro_do.domain.category.repository;


import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryInvitation;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryInvitationRepository extends JpaRepository<CategoryInvitation, Long> {

    List<CategoryInvitation> findAllByInviteeAndStatusOrderByCreatedAtDesc(User invitee, CategoryInvitationStatus status);

    /**
     * 카테고리, 초대대상자, 상태로 초대장 존재 여부 확인
     *
     * @param category 카테고리
     * @param invitee 초대대상자
     * @param status 초대 상태
     * @return 초대장 존재 여부
     */
    boolean existsByCategoryAndInviteeAndStatus(Category category, User invitee, CategoryInvitationStatus status);

    /**
     * 초대장 ID와 초대대상자 ID로 초대장 조회
     *
     * @param id 초대장 ID
     * @param inviteeId 초대대상자 ID
     * @return 초대장 Optional
     */
    Optional<CategoryInvitation> findByIdAndInviteeId(Long id, Long inviteeId);

    /**
     * 초대대상자와 상태로 초대장 목록 조회
     *
     * @param invitee 초대대상자
     * @param status 초대 상태
     * @return 초대장 목록
     */
    List<CategoryInvitation> findByInviteeAndStatus(User invitee, CategoryInvitationStatus status);

    /**
     * 초대대상자 ID와 상태로 초대장 목록 조회
     *
     * @param inviteeId 초대대상자 ID
     * @param status 초대 상태
     * @return 초대장 목록
     */
    List<CategoryInvitation> findByInviteeIdAndStatus(Long inviteeId, CategoryInvitationStatus status);

    /**
     * 카테고리 ID와 초대 상태로 초대장 목록 조회
     *
     * @param categoryId 카테고리 ID
     * @param status 초대 상태
     * @return 초대장 목록
     */
    List<CategoryInvitation> findByCategoryIdAndStatus(Long categoryId, CategoryInvitationStatus status);
}
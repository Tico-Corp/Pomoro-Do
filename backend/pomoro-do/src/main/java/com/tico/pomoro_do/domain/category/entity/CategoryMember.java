package com.tico.pomoro_do.domain.category.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.common.entity.BaseTimeEntity;
import com.tico.pomoro_do.domain.category.enums.CategoryDeletionOption;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
import com.tico.pomoro_do.global.common.util.DateUtils;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "category_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 그룹에 소속된 사용자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryMemberRole role; // 그룹에서의 역할 (OWNER: 관리자, MEMBER: 멤버)

    @Enumerated(EnumType.STRING)
    @Column(name = "deletion_option", nullable = false)
    private CategoryDeletionOption deletionOption = CategoryDeletionOption.RETAIN_ALL;  // 그룹 탈퇴 시 개인의 삭제 옵션

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate; // 그룹 가입일

    @Column(name = "left_date")
    private LocalDate leftDate; // 그룹 탈퇴일 (NULL이면 현재 활동 중)

    @Builder
    public CategoryMember(Category category, User user, CategoryMemberRole role) {
        this.category = category;
        this.user = user;
        this.role = role;
        this.joinedDate = DateUtils.getBusinessDate();
    }

    /**
     * 그룹 멤버가 탈퇴할 때 호출되는 메서드.
     * 삭제 정책에 따라 처리 방식을 결정하고, 탈퇴 일자를 설정합니다.
     */
    public void leave(CategoryDeletionOption deletionOption) {
        if (this.leftDate != null) {
            throw new CustomException(ErrorCode.ALREADY_LEFT_CATEGORY);
        }
        this.deletionOption = deletionOption;
        this.leftDate = DateUtils.getBusinessDate();
    }

    public boolean isOwner() {
        return this.role == CategoryMemberRole.OWNER;
    }

    /**
     * 현재 그룹 멤버가 활성 상태인지 확인합니다.
     * 탈퇴일자가 null이면 아직 그룹에 남아있는 것으로 간주합니다.
     *
     * @return true if not yet left, false otherwise
     */
    public boolean isActive() {
        return this.leftDate == null;
    }

    public void updateRole(CategoryMemberRole newRole) {
        this.role = newRole;
    }
}
package com.tico.pomoro_do.domain.category.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.common.entity.BaseTimeEntity;
import com.tico.pomoro_do.domain.category.enums.CategoryDeletionOption;
import com.tico.pomoro_do.domain.category.enums.CategoryMemberRole;
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
    public CategoryMember(Category category, User user, CategoryMemberRole role, LocalDate joinedDate) {
        this.category = category;
        this.user = user;
        this.role = role;
        this.joinedDate = joinedDate;
    }
}
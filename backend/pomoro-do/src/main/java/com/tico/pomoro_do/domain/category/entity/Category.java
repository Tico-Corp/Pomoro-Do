package com.tico.pomoro_do.domain.category.entity;


import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import com.tico.pomoro_do.global.enums.CategoryType;
import com.tico.pomoro_do.global.enums.CategoryVisibility;
import com.tico.pomoro_do.global.enums.CategoryDeletionOption;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner; // 카테고리 소유자 (개인 사용자/그룹장)

    @Column(nullable = false)
    private String name;    // 카테고리 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;  // 카테고리 유형 (PERSONAL: 개인, GROUP: 그룹)

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;    // 카테고리 생성일

    @Column(name = "end_date")
    private LocalDate endDate;  // 카테고리 종료일 (NULL이면 무기한 지속)

    @Enumerated(EnumType.STRING)
    @Column(name = "deletion_option", nullable = false)
    private CategoryDeletionOption deletionOption = CategoryDeletionOption.RETAIN_ALL;
    // 카테고리 삭제 옵션 (KEEP_ALL: 전체 유지, KEEP_COMPLETED: 완료만 유지, REMOVE_ALL: 전체 삭제) -> 모든 그룹원에게 적용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryVisibility visibility;  // 카테고리 공개 범위 (PUBLIC: 공개, FOLLOWERS: 팔로워 공개, PRIVATE: 비공개)

    @Column(nullable = false, length = 7)
    private String color = "#FFC090";

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;  // 카테고리 삭제 여부 (논리 삭제)

    @Builder
    public Category(User owner, String name, CategoryType type, LocalDate startDate, CategoryVisibility visibility) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.visibility = visibility;
    }
}

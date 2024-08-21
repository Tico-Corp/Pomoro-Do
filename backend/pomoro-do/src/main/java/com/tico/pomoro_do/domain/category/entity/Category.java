package com.tico.pomoro_do.domain.category.entity;


import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.CategoryType;
import com.tico.pomoro_do.global.enums.CategoryVisibility;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "category")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User host;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryVisibility visibility;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder
    public Category(User host, String title, String color, CategoryVisibility visibility, CategoryType type){
        this.host = host;
        this.title = title;
        this.color = color;
        this.visibility = visibility;
        this.type = type;

    }
}

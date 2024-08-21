package com.tico.pomoro_do.domain.category.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.GroupInviteStatus;
import com.tico.pomoro_do.global.enums.GroupRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "group_member")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupInviteStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupRole role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder
    public GroupMember(Category category, User user, GroupInviteStatus status, GroupRole role){
        this.category = category;
        this.user = user;
        this.status = status;
        this.role = role;
    }

}
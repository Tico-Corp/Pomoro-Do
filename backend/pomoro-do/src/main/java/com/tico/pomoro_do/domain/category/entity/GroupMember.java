package com.tico.pomoro_do.domain.category.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.common.enums.CategoryVisibility;
import com.tico.pomoro_do.global.common.enums.GroupInviteStatus;
import com.tico.pomoro_do.global.common.enums.GroupRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_member")
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Category group;

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

}
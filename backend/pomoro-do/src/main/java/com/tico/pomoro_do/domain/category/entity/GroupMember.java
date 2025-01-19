package com.tico.pomoro_do.domain.category.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import com.tico.pomoro_do.global.enums.GroupInvitationStatus;
import com.tico.pomoro_do.global.enums.GroupRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "group_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember extends BaseTimeEntity {
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
    private GroupInvitationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupRole role;

    @Builder
    public GroupMember(Category category, User user, GroupInvitationStatus status, GroupRole role){
        this.category = category;
        this.user = user;
        this.status = status;
        this.role = role;
    }

}
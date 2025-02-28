package com.tico.pomoro_do.domain.category.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import com.tico.pomoro_do.global.enums.CategoryInvitationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_invitations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryInvitation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_invitation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviter;   // 초대 요청 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id", nullable = false)
    private User invitee;   // 초대 대상 사용자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryInvitationStatus status = CategoryInvitationStatus.PENDING;
    // 초대 상태 (PENDING: 대기, ACCEPTED: 수락, REJECTED: 거절)

    @Builder
    public CategoryInvitation(Category category, User inviter, User invitee) {
        this.category = category;
        this.inviter = inviter;
        this.invitee = invitee;
    }

    public void accept() {
        this.status = CategoryInvitationStatus.ACCEPTED;
    }

    public void reject() {
        this.status = CategoryInvitationStatus.REJECTED;
    }
}
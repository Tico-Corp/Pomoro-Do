package com.tico.pomoro_do.domain.category.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.common.entity.BaseTimeEntity;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorCode;
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

    /**
     * 초대 상태 업데이트 메소드
     *
     * @param decision 변경할 상태
     */
    public void respondToInvitation(CategoryInvitationStatus decision) {
        // 초대 중복 응답 방지
        if (this.status != CategoryInvitationStatus.PENDING) {
            throw new CustomException(ErrorCode.INVITATION_ALREADY_RESPONDED);
        }
        this.status = decision;
    }
}
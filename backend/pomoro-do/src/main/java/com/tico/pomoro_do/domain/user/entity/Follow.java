package com.tico.pomoro_do.domain.user.entity;

import com.tico.pomoro_do.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower; // 팔로우 요청을 한 사용자 (보낸사람)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private User following; // 팔로우 대상 사용자

    @Builder
    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}

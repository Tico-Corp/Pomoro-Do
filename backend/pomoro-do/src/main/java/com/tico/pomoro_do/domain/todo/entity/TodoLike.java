package com.tico.pomoro_do.domain.todo.entity;

import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "todo_likes")
public class TodoLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_state_id", nullable = false)
    private TodoState todoState;

    @Column(name = "user_id", nullable = false)
    private Long userId;    // 참조용 사용자 ID -> 좋아요 누른 유저 개수만 카운팅 -> 추가로 필요한 사용자 정보없음
}

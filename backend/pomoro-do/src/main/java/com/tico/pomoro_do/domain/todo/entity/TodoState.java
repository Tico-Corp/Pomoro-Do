package com.tico.pomoro_do.domain.todo.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.common.entity.BaseTimeEntity;
import com.tico.pomoro_do.domain.todo.enums.TodoStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "todo_states")
public class TodoState extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_state_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status = TodoStatus.TODO;

    @Column(name = "likes_count", nullable = false)
    private int likesCount = 0;     // 좋아요 수
}

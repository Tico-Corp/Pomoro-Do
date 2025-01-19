package com.tico.pomoro_do.domain.timer.entity;

import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import com.tico.pomoro_do.global.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "focus_session_todos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimerFocusSessionTodo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "focus_session_todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timer_session_id", nullable = false)
    private TimerSession timerSession;

    @Column(name = "todo_id", nullable = false)
    private Long todoId; // 참조용 할 일 ID

    @Column(name = "todo_title", nullable = false)
    private String todoTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "todo_status", nullable = false)
    private TodoStatus todoStatus = TodoStatus.TODO;

}
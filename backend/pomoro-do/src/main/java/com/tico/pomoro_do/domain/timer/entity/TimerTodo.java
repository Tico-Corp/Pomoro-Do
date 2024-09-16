package com.tico.pomoro_do.domain.timer.entity;

import com.tico.pomoro_do.domain.todo.entity.Todo;
import com.tico.pomoro_do.global.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "timer_todo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimerTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timer_id", nullable = false)
    private Timer timer;

    @Column(name = "todo_id", nullable = false)
    private Long todoId;

    @Column(name = "todo_title", nullable = false)
    private String todoTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
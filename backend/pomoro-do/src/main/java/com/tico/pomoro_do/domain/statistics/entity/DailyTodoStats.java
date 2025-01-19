package com.tico.pomoro_do.domain.statistics.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "daily_todo_stats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTodoStats extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_todo_stat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "stat_date", nullable = false)
    private LocalDate statDate;

    @Column(name = "incomplete_todos_count", nullable = false)
    private int incompleteTodosCount = 0;
//
//    @Column(name = "created_at", updatable = false, nullable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt = LocalDateTime.now();
}

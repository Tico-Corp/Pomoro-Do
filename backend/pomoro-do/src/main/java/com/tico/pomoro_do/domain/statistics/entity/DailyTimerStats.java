package com.tico.pomoro_do.domain.statistics.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_timer_stats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTimerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_timer_stat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "stat_date", nullable = false)
    private LocalDate statDate;

    @Column(name = "target_focus_seconds", nullable = false)
    private int targetFocusSeconds = 28800;

    @Column(name = "total_focus_seconds", nullable = false)
    private int totalFocusSeconds = 0;

    @Column(name = "total_break_seconds", nullable = false)
    private int totalBreakSeconds = 0;

    @Column(name = "max_focus_seconds", nullable = false)
    private int maxFocusSeconds = 0;

    @Column(name = "max_break_seconds", nullable = false)
    private int maxBreakSeconds = 0;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
}
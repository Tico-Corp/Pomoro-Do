package com.tico.pomoro_do.domain.statistics.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_stats")
public class DailyStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "target_focus_time", nullable = false)
    private int targetFocusTime;

    @Column(name = "total_focus_time", nullable = false)
    private int totalFocusTime;

    @Column(name = "total_break_time", nullable = false)
    private int totalBreakTime;

    @Column(name = "max_focus_time", nullable = false)
    private int maxFocusTime;

    @Column(name = "max_break_time", nullable = false)
    private int maxBreakTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
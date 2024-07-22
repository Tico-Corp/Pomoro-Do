package com.tico.pomoro_do.domain.timer.entity;


import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.TimerType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "timer")
public class Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int duration; // in seconds

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimerType type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}

package com.tico.pomoro_do.domain.timer.entity;


import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import com.tico.pomoro_do.domain.timer.enums.TimerSessionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "timer_sessions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimerSession extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_session_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false)
    private TimerSessionType sessionType;   // 타이머 세션 유형 (FOCUS: 집중, BREAK: 휴식)

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate; // 세션 날짜

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // 세션 시작 시각

    @Column(name = "end_time")
    private LocalDateTime endTime;  // 세션 종료 시각

    @Column(name = "duration_seconds", nullable = false)
    private int durationSeconds;    // 세션 지속 시간 (초)

}

package com.tico.pomoro_do.domain.timer.entity;

import com.tico.pomoro_do.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "timer_settings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TimerSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_setting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "target_focus_time", nullable = false)
    private LocalTime targetFocusTime = LocalTime.of(8, 0); // 기본값: 08:00:00

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 수정 시각
}

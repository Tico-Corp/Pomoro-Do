package com.tico.pomorodo.data.model

import com.tico.pomorodo.common.util.Converters
import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDateTime

data class DailyTimerData(
    val statDate: LocalDate,
    val targetFocusTime: LocalTime,
    val totalFocusTime: LocalTime,
    val totalBreakTime: LocalTime,
    val maxFocusTime: LocalTime,
    val maxBreakTime: LocalTime,
    val inCompleteTodosCount: Int,
    val updatedAt: LocalDateTime
) {
    constructor(statDate: LocalDate) : this(
        statDate = statDate,
        targetFocusTime = LocalTime(0, 0, 0),
        totalFocusTime = LocalTime(0, 0, 0),
        totalBreakTime = LocalTime(0, 0, 0),
        maxFocusTime = LocalTime(0, 0, 0),
        maxBreakTime = LocalTime(0, 0, 0),
        inCompleteTodosCount = 0,
        updatedAt = Clock.System.now().toLocalDateTime(Converters.TIME_ZONE),
    )
}

fun DailyTimerData.toDailyTimerEntity() = DailyTimerEntity(
    statDate = statDate,
    targetFocusTime = targetFocusTime,
    totalFocusTime = totalFocusTime,
    totalBreakTime = totalBreakTime,
    maxFocusTime = maxFocusTime,
    maxBreakTime = maxBreakTime,
    inCompleteTodosCount = inCompleteTodosCount,
    updatedAt = updatedAt,
)
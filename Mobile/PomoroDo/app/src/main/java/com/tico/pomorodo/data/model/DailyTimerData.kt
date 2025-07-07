package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class DailyTimerData(
    val id: Int,
    val userId: Int,
    val statDate: LocalDate,
    val targetFocusTime: LocalTime,
    val totalFocusTime: LocalTime,
    val totalBreakTime: LocalTime,
    val maxFocusTime: LocalTime,
    val maxBreakTime: LocalTime,
    val inCompleteTodosCount: Int,
    val updatedAt: Long
)

fun DailyTimerData.toDailyTimerEntity() = DailyTimerEntity(
    id = id,
    userId = userId,
    statDate = statDate,
    targetFocusTime = targetFocusTime,
    totalFocusTime = totalFocusTime,
    totalBreakTime = totalBreakTime,
    maxFocusTime = maxFocusTime,
    maxBreakTime = maxBreakTime,
    inCompleteTodosCount = inCompleteTodosCount,
    updatedAt = updatedAt,
)
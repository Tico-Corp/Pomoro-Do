package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import kotlinx.datetime.LocalTime

data class TimerSettingData(
    val id: Int = 0,
    val userId: Int,
    val time: LocalTime,
    val updatedAt: Long
)

fun TimerSettingData.toTimerSettingEntity() = TimerSettingEntity(
    id = id,
    userId = userId,
    time = time,
    updatedAt = updatedAt
)
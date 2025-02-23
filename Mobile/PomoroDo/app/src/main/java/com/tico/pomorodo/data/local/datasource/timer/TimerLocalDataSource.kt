package com.tico.pomorodo.data.local.datasource.timer

import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import kotlinx.coroutines.flow.Flow

interface TimerLocalDataSource {
    suspend fun getTargetTime(userId: Int): Flow<TimerSettingEntity>

    suspend fun updateTargetTime(timerSettingEntity: TimerSettingEntity)
}
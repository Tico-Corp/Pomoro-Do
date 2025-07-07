package com.tico.pomorodo.data.local.datasource.timer

import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import kotlinx.coroutines.flow.Flow

interface TimerLocalDataSource {
    suspend fun insertConcentrationGoal(dailyTimerEntity: DailyTimerEntity)

    suspend fun getConcentrationGoal(userId: Int): Flow<DailyTimerEntity?>

    suspend fun updateTargetFocusTime(dailyTimerEntity: DailyTimerEntity)
}
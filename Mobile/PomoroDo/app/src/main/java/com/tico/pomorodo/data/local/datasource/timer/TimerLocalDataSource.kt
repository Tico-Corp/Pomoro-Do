package com.tico.pomorodo.data.local.datasource.timer

import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TimerLocalDataSource {
    suspend fun insertConcentrationGoal(dailyTimerEntity: DailyTimerEntity)

    suspend fun getDailyTimerData(userId: Int, statDate: LocalDate): Flow<DailyTimerEntity?>

    suspend fun updateTargetFocusTime(dailyTimerEntity: DailyTimerEntity)
}
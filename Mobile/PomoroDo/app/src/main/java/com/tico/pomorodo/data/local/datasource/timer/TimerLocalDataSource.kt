package com.tico.pomorodo.data.local.datasource.timer

import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import kotlinx.coroutines.flow.Flow

interface TimerLocalDataSource {
    suspend fun insertConcentrationGoal(timerSettingEntity: TimerSettingEntity)

    suspend fun getConcentrationGoal(userId: Int): Flow<TimerSettingEntity?>

    suspend fun updateConcentrationGoal(timerSettingEntity: TimerSettingEntity)
}
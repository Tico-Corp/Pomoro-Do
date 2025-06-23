package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.TimerSettingData
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    suspend fun insertConcentrationGoal(timerSettingData: TimerSettingData)

    suspend fun getConcentrationGoal(userId: Int): Flow<Resource<TimerSettingData?>>

    suspend fun updateConcentrationGoal(timerSettingData: TimerSettingData)
}
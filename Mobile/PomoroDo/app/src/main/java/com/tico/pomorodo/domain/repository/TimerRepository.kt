package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.DailyTimerData
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TimerRepository {
    suspend fun insertConcentrationGoal(dailyTimerData: DailyTimerData)

    suspend fun getDailyTimerData(statDate: LocalDate): Flow<Resource<DailyTimerData?>>

    suspend fun updateTargetFocusTime(dailyTimerData: DailyTimerData)
}
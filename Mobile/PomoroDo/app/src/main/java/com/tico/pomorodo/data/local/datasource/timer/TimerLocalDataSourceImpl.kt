package com.tico.pomorodo.data.local.datasource.timer

import com.tico.pomorodo.data.local.dao.TimerDao
import com.tico.pomorodo.data.local.entity.DailyTimerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class TimerLocalDataSourceImpl @Inject constructor(private val timerDao: TimerDao) :
    TimerLocalDataSource {
    override suspend fun insertConcentrationGoal(dailyTimerEntity: DailyTimerEntity) =
        timerDao.insertConcentrationGoal(dailyTimerEntity)

    override suspend fun getDailyTimerData(statDate: LocalDate): Flow<DailyTimerEntity?> =
        timerDao.getDailyTimerData(statDate)

    override suspend fun updateTargetFocusTime(dailyTimerEntity: DailyTimerEntity) =
        timerDao.updateTargetFocusTime(dailyTimerEntity)
}
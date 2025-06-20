package com.tico.pomorodo.data.local.datasource.timer

import com.tico.pomorodo.data.local.dao.TimerDao
import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimerLocalDataSourceImpl @Inject constructor(private val timerDao: TimerDao) :
    TimerLocalDataSource {
    override suspend fun insertConcentrationGoal(timerSettingEntity: TimerSettingEntity) =
        timerDao.insertConcentrationGoal(timerSettingEntity)

    override suspend fun getConcentrationGoal(userId: Int): Flow<TimerSettingEntity?> =
        timerDao.getConcentrationGoal(userId)

    override suspend fun updateConcentrationGoal(timerSettingEntity: TimerSettingEntity) =
        timerDao.updateConcentrationGoal(timerSettingEntity)
}
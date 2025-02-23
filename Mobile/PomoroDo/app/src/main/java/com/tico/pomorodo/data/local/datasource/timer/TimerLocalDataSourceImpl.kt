package com.tico.pomorodo.data.local.datasource.timer

import com.tico.pomorodo.data.local.dao.TimerDao
import com.tico.pomorodo.data.local.entity.TimerSettingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimerLocalDataSourceImpl @Inject constructor(private val timerDao: TimerDao) :
    TimerLocalDataSource {
    override suspend fun getTargetTime(userId: Int): Flow<TimerSettingEntity> =
        timerDao.getTargetTime(userId)

    override suspend fun updateTargetTime(timerSettingEntity: TimerSettingEntity) =
        timerDao.updateTargetTime(timerSettingEntity)
}
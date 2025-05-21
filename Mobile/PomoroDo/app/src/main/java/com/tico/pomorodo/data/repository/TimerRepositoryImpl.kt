package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.datasource.timer.TimerLocalDataSource
import com.tico.pomorodo.data.local.entity.toTimerSettingData
import com.tico.pomorodo.data.model.TimerSettingData
import com.tico.pomorodo.data.model.toTimerSettingEntity
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val timerLocalDataSource: TimerLocalDataSource,
) : TimerRepository {
    override suspend fun getTargetTime(userId: Int): Flow<Resource<TimerSettingData>> = flow {
        emit(Resource.Loading)

        if (networkHelper.isNetworkConnected()) {
            // TODO: 서버에서 목표 집중 시간 받아오기
        } else {
            val data = timerLocalDataSource.getTargetTime(userId).map {
                wrapToResource(Dispatchers.IO) { it.toTimerSettingData() }
            }

            emitAll(data)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateTargetTime(timerSettingData: TimerSettingData) {
        val timerSettingEntity = timerSettingData.toTimerSettingEntity()
        if (networkHelper.isNetworkConnected()) {
            // TODO: 서버로 목표 집중 시간 전송
        } else {
            timerLocalDataSource.updateTargetTime(timerSettingEntity)
        }
    }
}
package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.datasource.calendar.CalendarLocalDataSource
import com.tico.pomorodo.data.local.entity.toCalendarDate
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.toCalendarDateEntity
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarLocalDataSource: CalendarLocalDataSource,
    private val networkHelper: NetworkHelper
) : CalendarRepository {
    override suspend fun getCalendarDateForMonth(
        startEpochDays: Int,
        endEpochDays: Int
    ): Flow<Resource<List<CalendarDate>>> = flow {
        emit(Resource.Loading)

        if (networkHelper.isNetworkConnected()) {
            // TODO:bring network data
        } else {
            emitAll(
                calendarLocalDataSource.getCalendarDateForMonth(
                    startEpochDays,
                    endEpochDays
                ).map {
                    wrapToResource(Dispatchers.IO) {
                        it.map { entity -> entity.toCalendarDate() }
                    }
                })
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateCalendarDateForMonth(calendarDate: CalendarDate) {
        if(networkHelper.isNetworkConnected()){

        }else{
            calendarLocalDataSource.updateCalendarDateForMonth(calendarDate.toCalendarDateEntity())
        }
    }

    override suspend fun insertCalendarDateForMonth(calendarDate: CalendarDate) {
        if(networkHelper.isNetworkConnected()){

        }else{
            calendarLocalDataSource.insertCalendarDateForMonth(calendarDate.toCalendarDateEntity())
        }
    }
}
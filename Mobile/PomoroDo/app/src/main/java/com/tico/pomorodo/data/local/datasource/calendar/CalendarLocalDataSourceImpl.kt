package com.tico.pomorodo.data.local.datasource.calendar

import com.tico.pomorodo.data.local.dao.CalendarDao
import com.tico.pomorodo.data.local.entity.CalendarDateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalendarLocalDataSourceImpl @Inject constructor(private val calendarDao: CalendarDao) :
    CalendarLocalDataSource {
    override suspend fun getCalendarDateForMonth(
        startEpochDays: Int,
        endEpochDays: Int
    ): Flow<List<CalendarDateEntity>> {
        return calendarDao.getCalendarDateForMonth(startEpochDays, endEpochDays)
    }

    override suspend fun updateCalendarDateForMonth(calendarDate: CalendarDateEntity) {
        calendarDao.updateCalendarDateForMonth(calendarDate)
    }

    override suspend fun insertCalendarDateForMonth(calendarDate: CalendarDateEntity) {
        calendarDao.insert(calendarDate)
    }
}
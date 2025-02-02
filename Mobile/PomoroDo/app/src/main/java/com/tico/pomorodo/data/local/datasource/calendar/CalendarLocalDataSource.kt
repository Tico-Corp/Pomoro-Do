package com.tico.pomorodo.data.local.datasource.calendar

import com.tico.pomorodo.data.local.entity.CalendarDateEntity
import kotlinx.coroutines.flow.Flow

interface CalendarLocalDataSource {
    suspend fun getCalendarDateForMonth(
        startEpochDays: Int,
        endEpochDays: Int
    ): Flow<List<CalendarDateEntity>>

    suspend fun updateCalendarDateForMonth(calendarDate: CalendarDateEntity)

    suspend fun insertCalendarDateForMonth(calendarDate: CalendarDateEntity)
}
package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    suspend fun getCalendarDateForMonth(
        startEpochDays: Int,
        endEpochDays: Int
    ): Flow<Resource<List<CalendarDate>>>

    suspend fun updateCalendarDateForMonth(calendarDate: CalendarDate)

    suspend fun insertCalendarDateForMonth(calendarDate: CalendarDate)
}
package com.tico.pomorodo.domain.usecase.calendar

import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCalendarDateForMonthUseCase @Inject constructor(private val calendarRepository: CalendarRepository) {
    suspend operator fun invoke(
        startEpochDays: Int,
        endEpochDays: Int
    ): Flow<Resource<List<CalendarDate>>> =
        withContext(Dispatchers.IO) {
            calendarRepository.getCalendarDateForMonth(startEpochDays, endEpochDays)
        }
}
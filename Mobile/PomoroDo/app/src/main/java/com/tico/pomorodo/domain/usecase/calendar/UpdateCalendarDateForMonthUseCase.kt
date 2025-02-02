package com.tico.pomorodo.domain.usecase.calendar

import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.domain.repository.CalendarRepository
import javax.inject.Inject

class UpdateCalendarDateForMonthUseCase @Inject constructor(private val calendarRepository: CalendarRepository) {
    suspend operator fun invoke(calendarDate: CalendarDate) {
        calendarRepository.updateCalendarDateForMonth(calendarDate)
    }
}
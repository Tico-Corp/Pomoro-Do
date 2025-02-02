package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.CalendarDateEntity
import kotlinx.datetime.LocalDate

data class CalendarDate(
    val date: LocalDate,
    val focusState: CalendarFocusState = CalendarFocusState.WHITE,
    val remainedTodoCount: Int = 0,
    val totalCount: Int = 0,
)

fun CalendarDate.toCalendarDateEntity() = CalendarDateEntity(
    date = date,
    focusState = focusState,
    remainedTodoCount = remainedTodoCount,
    totalCount = totalCount
)
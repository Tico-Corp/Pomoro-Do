package com.tico.pomorodo.data.model

import kotlinx.datetime.LocalDate

data class CalendarDate(
    val date: LocalDate,
    val focusState: CalendarFocusState = CalendarFocusState.WHITE,
    val remainedTodoCount: Int = 0,
    val totalCount: Int = 0,
)
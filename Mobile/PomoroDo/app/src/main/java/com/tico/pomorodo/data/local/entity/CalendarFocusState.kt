package com.tico.pomorodo.data.local.entity

import androidx.annotation.StringRes
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_GREEN
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_ORANGE
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_RED
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_WHITE
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_YELLOW

enum class CalendarFocusState(
    val iconString: String,
    @StringRes val iconTextId: Int,
) {
    WHITE(
        iconString = IC_CALENDAR_DATE_WHITE,
        iconTextId = R.string.content_calendar_date_white,
    ),
    GREEN(
        iconString = IC_CALENDAR_DATE_GREEN,
        iconTextId = R.string.content_calendar_date_green,
    ),
    YELLOW(
        iconString = IC_CALENDAR_DATE_YELLOW,
        iconTextId = R.string.content_calendar_date_yellow,
    ),
    ORANGE(
        iconString = IC_CALENDAR_DATE_ORANGE,
        iconTextId = R.string.content_calendar_date_orange,
    ),
    RED(
        iconString = IC_CALENDAR_DATE_RED,
        iconTextId = R.string.content_calendar_date_red,
    )
}

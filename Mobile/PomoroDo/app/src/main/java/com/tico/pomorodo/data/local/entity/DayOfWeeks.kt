package com.tico.pomorodo.data.local.entity

import androidx.annotation.StringRes
import com.tico.pomorodo.R


enum class DayOfWeeks(@StringRes val dayId: Int) {
    SUNDAY(R.string.content_calendar_sunday),
    MONDAY(R.string.content_calendar_monday),
    TUESDAY(R.string.content_calendar_tuesday),
    WEDNESDAY(R.string.content_calendar_wednesday),
    THURSDAY(R.string.content_calendar_thursday),
    FRIDAY(R.string.content_calendar_friday),
    SATURDAY(R.string.content_calendar_saturday),
}
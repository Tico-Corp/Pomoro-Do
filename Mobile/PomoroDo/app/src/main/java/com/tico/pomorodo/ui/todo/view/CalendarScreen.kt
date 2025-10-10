package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.common.util.Converters.Companion.TIME_ZONE
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.CalendarFocusState
import com.tico.pomorodo.data.model.DayOfWeeks
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.atEndOfMonth
import com.tico.pomorodo.ui.common.view.atStartOfMonth
import com.tico.pomorodo.ui.common.view.clickableWithoutRipple
import com.tico.pomorodo.ui.common.view.daysInMonth
import com.tico.pomorodo.ui.common.view.weekOfMonth
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcAllCheckTodoState
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcCalendarChecked
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcFavoriteFilled
import com.tico.pomorodo.ui.theme.IC_ARROW_BACK
import com.tico.pomorodo.ui.theme.IC_ARROW_FRONT
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_RED
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DROP_DOWN
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.math.ceil

@Composable
fun TodoCalendarScreen(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    monthlyLikedNumber: Int,
    monthlyFullFocusNumber: Int,
    monthlyAllCheckedNumber: Int,
    calendarDates: List<CalendarDate>
) {
    var showMonthly by remember { mutableStateOf(false) }

    val currentDate = Clock.System.todayIn(TIME_ZONE)

    var currentMonth by remember {
        mutableStateOf(LocalDate(currentDate.year, currentDate.month, 1))
    }
    val calendarTitle = if (showMonthly) {
        stringResource(
            id = R.string.content_calendar_monthly_title,
            selectedDate.year,
            selectedDate.monthNumber
        )
    } else {
        stringResource(
            id = R.string.content_calendar_weekly_title,
            selectedDate.year,
            selectedDate.monthNumber,
            selectedDate.weekOfMonth()
        )
    }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        CalendarStatus(
            title = calendarTitle,
            onBackButtonClicked = {
                if (showMonthly) {
                    currentMonth = currentMonth.minus(DatePeriod(months = 1))
                    onDateSelected(currentMonth.atEndOfMonth())
                } else {
                    onDateSelected(selectedDate.minus(DatePeriod(days = 7)))
                }
            },
            onFrontButtonClicked = {
                if (showMonthly) {
                    currentMonth = currentMonth.plus(DatePeriod(months = 1))
                    onDateSelected(currentMonth.atStartOfMonth())
                } else {
                    onDateSelected(selectedDate.plus(DatePeriod(days = 7)))
                }
            },
            onDropDownClicked = { showMonthly = !showMonthly },
            onTitleDateClicked = {
                currentMonth = LocalDate(currentDate.year, currentDate.month, 1)
                onDateSelected(currentDate)
            },
            monthlyLikedNumber = monthlyLikedNumber,
            monthlyFullFocusNumber = monthlyFullFocusNumber,
            monthlyAllCheckedNumber = monthlyAllCheckedNumber
        )
        CalendarDayOfWeek()

        if (showMonthly) {
            MonthlyCalendar(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                today = currentDate,
                onDateSelected = onDateSelected,
                calendarDates = calendarDates
            )
        } else {
            WeeklyCalendar(
                selectedDate = selectedDate,
                today = currentDate,
                onDateSelected = onDateSelected,
                calendarDates = calendarDates
            )
        }
    }
}

@Composable
fun CalendarStatus(
    title: String,
    onBackButtonClicked: () -> Unit,
    onFrontButtonClicked: () -> Unit,
    onDropDownClicked: () -> Unit,
    onTitleDateClicked: () -> Unit,
    monthlyLikedNumber: Int,
    monthlyFullFocusNumber: Int,
    monthlyAllCheckedNumber: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CalendarDate(
                title = title,
                onBackButtonClicked = onBackButtonClicked,
                onFrontButtonClicked = onFrontButtonClicked,
                onTitleDateClicked = onTitleDateClicked
            )
            CalendarTotals(
                monthlyLikedNumber = monthlyLikedNumber,
                monthlyFullFocusNumber = monthlyFullFocusNumber,
                monthlyAllCheckedNumber = monthlyAllCheckedNumber
            )
        }
        SimpleIconButton(
            size = 19,
            imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_CALENDAR_DROP_DOWN]),
            contentDescriptionId = R.string.content_calendar_drop_down,
            onClickedListener = onDropDownClicked,
            enabled = true
        )
    }
}

@Composable
fun CalendarDate(
    title: String,
    onBackButtonClicked: () -> Unit,
    onFrontButtonClicked: () -> Unit,
    onTitleDateClicked: () -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SimpleIconButton(
            size = 19,
            imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_ARROW_BACK]),
            contentDescriptionId = R.string.content_calendar_back,
            onClickedListener = onBackButtonClicked,
            enabled = true
        )
        SimpleText(
            modifier = Modifier.clickableWithoutRipple { onTitleDateClicked() },
            text = title,
            style = PomoroDoTheme.typography.laundryGothicBold12,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        SimpleIconButton(
            size = 19,
            imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_ARROW_FRONT]),
            contentDescriptionId = R.string.content_calendar_front,
            onClickedListener = onFrontButtonClicked,
            enabled = true
        )
    }
}

@Composable
fun CalendarTotals(
    monthlyLikedNumber: Int,
    monthlyFullFocusNumber: Int,
    monthlyAllCheckedNumber: Int
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        CalendarMonthlyTotalItem(
            requireNotNull(PomoroDoTheme.iconPack[IC_CALENDAR_DATE_RED]),
            R.string.content_calendar_full_focus,
            monthlyFullFocusNumber
        )
        CalendarMonthlyTotalItem(
            IconPack.IcAllCheckTodoState,
            R.string.content_calendar_all_checked,
            monthlyAllCheckedNumber
        )
        CalendarMonthlyTotalItem(
            IconPack.IcFavoriteFilled,
            R.string.content_calendar_liked,
            monthlyLikedNumber
        )
    }
}

@Composable
private fun CalendarMonthlyTotalItem(
    imageVector: ImageVector,
    contentDescriptionId: Int,
    count: Int
) {
    SimpleIcon(
        size = 14,
        imageVector = imageVector,
        contentDescriptionId = contentDescriptionId
    )
    SimpleText(
        text = count.toString(),
        style = PomoroDoTheme.typography.laundryGothicBold12,
        color = PomoroDoTheme.colorScheme.onBackground
    )
}

@Composable
fun CalendarDayOfWeek() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DayOfWeeks.entries.forEach { dayOfWeek ->
            SimpleText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(id = dayOfWeek.dayId),
                style = PomoroDoTheme.typography.singleDayRegular18,
                textAlign = TextAlign.Center,
                color = PomoroDoTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun WeeklyCalendar(
    selectedDate: LocalDate,
    today: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    calendarDates: List<CalendarDate>
) {
    val startOfWeek =
        selectedDate.minus((selectedDate.dayOfWeek.isoDayNumber % 7).toLong(), DateTimeUnit.DAY)
    val daysInWeek = (0..6).map { startOfWeek.plus(it, DateTimeUnit.DAY) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (date in daysInWeek) {
            CalendarDayItem(
                date = date,
                selectedDate = selectedDate,
                today = today,
                onDateSelected = onDateSelected,
                calendarDate = calendarDates.find { it.date == date } ?: CalendarDate(date)
            )
        }
    }
}

@Composable
fun CalendarDayItem(
    date: LocalDate,
    selectedDate: LocalDate,
    today: LocalDate,
    focusState: CalendarFocusState = CalendarFocusState.WHITE,
    onDateSelected: (LocalDate) -> Unit,
    calendarDate: CalendarDate
) {
    val isSelected = date == selectedDate
    val isToday = date == today

    Column(
        modifier = Modifier
            .clickableWithoutRipple { onDateSelected(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            SimpleIcon(
                size = 36,
                imageVector = requireNotNull(PomoroDoTheme.iconPack[focusState.iconString]),
                contentDescriptionId = focusState.iconTextId
            )
            if (calendarDate.remainedTodoCount > 0) {
                SimpleText(
                    modifier = Modifier.offset(y = 4.dp),
                    text = calendarDate.remainedTodoCount.toString(),
                    style = PomoroDoTheme.typography.singleDayRegular18,
                    color = Color.Black
                )
            } else {
                if (calendarDate.totalCount > 0) {
                    SimpleIcon(
                        modifier = Modifier.offset(y = 4.dp),
                        size = 16,
                        imageVector = IconPack.IcCalendarChecked,
                        contentDescriptionId = focusState.iconTextId
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            if (isToday && isSelected) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(PomoroDoTheme.colorScheme.primaryContainer)
                )
            } else if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(PomoroDoTheme.colorScheme.onBackground)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            SimpleText(
                modifier = Modifier
                    .align(Alignment.Center),
                text = date.dayOfMonth.toString(),
                style = PomoroDoTheme.typography.singleDayRegular12,
                color = if (isSelected) {
                    PomoroDoTheme.colorScheme.background
                } else if (isToday) {
                    PomoroDoTheme.colorScheme.primaryContainer
                } else {
                    PomoroDoTheme.colorScheme.onBackground
                },
            )
        }
    }
}


@Composable
fun MonthlyCalendar(
    currentMonth: LocalDate,
    selectedDate: LocalDate,
    today: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    calendarDates: List<CalendarDate>
) {
    val daysInMonth = currentMonth.daysInMonth()
    val firstDayOfMonth = currentMonth.dayOfWeek.isoDayNumber % 7
    val totalWeeks = ceil((firstDayOfMonth + daysInMonth) / 7.0).toInt()

    for (week in 0 until totalWeeks) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            for (dayOfWeek in 0 until 7) {
                val day = week * 7 + dayOfWeek - firstDayOfMonth + 1
                if (day in 1..daysInMonth) {
                    CalendarDayItem(
                        date = currentMonth.plus(DatePeriod(days = day - 1)),
                        selectedDate = selectedDate,
                        today = today,
                        onDateSelected = onDateSelected,
                        calendarDate = calendarDates.find {
                            it.date == LocalDate(
                                currentMonth.year,
                                currentMonth.monthNumber,
                                day
                            )
                        } ?: CalendarDate(
                            LocalDate(
                                currentMonth.year,
                                currentMonth.monthNumber,
                                day
                            )
                        )
                    )
                } else {
                    Spacer(modifier = Modifier.size(36.dp))
                }
            }
        }
    }
}
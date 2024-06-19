package com.tico.pomorodo.ui.todo.view

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.iconpack.IcAllCheckTodoState
import com.tico.pomorodo.ui.iconpack.IcCalendarChecked
import com.tico.pomorodo.ui.iconpack.IcFavoriteFilled
import com.tico.pomorodo.ui.theme.IC_ARROW_BACK
import com.tico.pomorodo.ui.theme.IC_ARROW_FRONT
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_GREEN
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_ORANGE
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_RED
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_WHITE
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DATE_YELLOW
import com.tico.pomorodo.ui.theme.IC_CALENDAR_DROP_DOWN
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
fun CalendarStatus(
    titleDate: String,
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
                titleDate = titleDate,
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
            imageVector = PomoroDoTheme.iconPack[IC_CALENDAR_DROP_DOWN]!!,
            contentDescriptionId = R.string.content_calendar_drop_down,
            onClickedListener = onDropDownClicked,
            enabled = true
        )
    }
}

@Composable
fun CalendarDate(
    onBackButtonClicked: () -> Unit,
    onFrontButtonClicked: () -> Unit,
    titleDate: String,
    onTitleDateClicked: () -> Unit,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SimpleIconButton(
            size = 19,
            imageVector = PomoroDoTheme.iconPack[IC_ARROW_BACK]!!,
            contentDescriptionId = R.string.content_calendar_back,
            onClickedListener = onBackButtonClicked,
            enabled = true
        )
        SimpleText(
            modifier = Modifier.clickable { onTitleDateClicked() },
            text = titleDate,
            style = PomoroDoTheme.typography.laundryGothicBold12,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        SimpleIconButton(
            size = 19,
            imageVector = PomoroDoTheme.iconPack[IC_ARROW_FRONT]!!,
            contentDescriptionId = R.string.content_calendar_front,
            onClickedListener = onFrontButtonClicked,
            enabled = true
        )
    }
}

@Composable
fun CalendarTotals(
    monthlyFullFocusNumber: Int,
    monthlyAllCheckedNumber: Int,
    monthlyLikedNumber: Int
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        CalendarMonthlyTotalItem(
            PomoroDoTheme.iconPack[IC_CALENDAR_DATE_RED]!!,
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
fun MonthlyCalendar(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    today: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    // 이번달 날짜 개수
    val daysInMonth = currentMonth.lengthOfMonth()
    // 0(sun) ~ 6(sat)
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7

    val totalWeeks = (firstDayOfMonth + daysInMonth + 6) / 7
    for (week in 0 until totalWeeks) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            for (dayOfWeek in 0 until 7) {
                val day = week * 7 + dayOfWeek - firstDayOfMonth + 1
                if (day in 1..daysInMonth) {
                    CalendarDayItem(
                        day = day,
                        date = currentMonth.atDay(day),
                        selectedDate = selectedDate,
                        today = today,
                        onDateSelected = onDateSelected
                    )
                } else {
                    Spacer(modifier = Modifier.size(36.dp))
                }
            }
        }
    }
}

@Composable
fun WeeklyCalendar(
    selectedDate: LocalDate,
    today: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val startOfWeek = selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    val daysInWeek = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        for (date in daysInWeek) {
            CalendarDayItem(
                day = date.dayOfMonth,
                date = date,
                selectedDate = selectedDate,
                today = today,
                onDateSelected = onDateSelected
            )
        }
    }
}

@Composable
fun CalendarDayItem(
    day: Int = 1,
    remainedNumber: Int = 3,
    date: LocalDate,
    selectedDate: LocalDate,
    today: LocalDate,
    focusState: CalendarFocusState = CalendarFocusState.WHITE,
    onDateSelected: (LocalDate) -> Unit
) {
    val isSelected = date == selectedDate
    val isToday = date == today

    Column(
        modifier = Modifier
            .clickable { onDateSelected(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            SimpleIcon(
                size = 36,
                imageVector = PomoroDoTheme.iconPack[focusState.iconString]!!,
                contentDescriptionId = focusState.iconTextId
            )
            if (remainedNumber > 0) {
                SimpleText(
                    modifier = Modifier.offset(y = 4.dp),
                    text = remainedNumber.toString(),
                    style = PomoroDoTheme.typography.singleDayRegular18,
                    color = Color.Black
                )
            } else {
                SimpleIcon(
                    modifier = Modifier.offset(y = 4.dp),
                    size = 16,
                    imageVector = IconPack.IcCalendarChecked,
                    contentDescriptionId = focusState.iconTextId
                )
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
                        .background(PomoroDoTheme.colorScheme.primaryContainer)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            SimpleText(
                modifier = Modifier
                    .align(Alignment.Center),
                text = day.toString(),
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

enum class DayOfWeeks(@StringRes val dayId: Int) {
    SUNDAY(R.string.content_calendar_sunday),
    MONDAY(R.string.content_calendar_monday),
    TUESDAY(R.string.content_calendar_tuesday),
    WEDNESDAY(R.string.content_calendar_wednesday),
    THURSDAY(R.string.content_calendar_thursday),
    FRIDAY(R.string.content_calendar_friday),
    SATURDAY(R.string.content_calendar_saturday),
}

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

@Preview
@Composable
fun TodoCalendarScreen() {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showMonthly by remember { mutableStateOf(true) }
    val monthlyLikedNumber by remember { mutableIntStateOf(3) }
    val monthlyFullFocusNumber by remember { mutableIntStateOf(3) }
    val monthlyAllCheckedNumber by remember { mutableIntStateOf(3) }
    val today = LocalDate.now()

    val year = selectedDate.year
    val month = selectedDate.month.value
    val weekOfMonth = selectedDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth())

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        CalendarStatus(
            titleDate = if (showMonthly) {
                currentMonth.format(DateTimeFormatter.ofPattern("yyyy년 M월"))
            } else {
                stringResource(
                    id = R.string.content_calendar_weekly_title,
                    year,
                    month,
                    weekOfMonth
                )
            },
            onBackButtonClicked = {
                if (showMonthly) {
                    currentMonth = currentMonth.minusMonths(1)
                    selectedDate = currentMonth.atEndOfMonth()
                } else {
                    selectedDate = selectedDate.minusWeeks(1)
                    currentMonth = YearMonth.of(selectedDate.year, selectedDate.month)
                }
            },
            onFrontButtonClicked = {
                if (showMonthly) {
                    currentMonth = currentMonth.plusMonths(1)
                    selectedDate = currentMonth.atDay(1)
                } else {
                    selectedDate = selectedDate.plusWeeks(1)
                    currentMonth = YearMonth.of(selectedDate.year, selectedDate.month)
                }
            },
            onDropDownClicked = { showMonthly = !showMonthly },
            onTitleDateClicked = {
                currentMonth = YearMonth.now()
                selectedDate = LocalDate.now()
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
                today = today,
                onDateSelected = { selectedDate = it }
            )
        } else {
            WeeklyCalendar(
                selectedDate = selectedDate,
                today = today,
                onDateSelected = { selectedDate = it }
            )
        }
    }
}
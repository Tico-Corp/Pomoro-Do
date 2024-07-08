package com.tico.pomorodo.ui.history.view

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.DayOfWeeks
import com.tico.pomorodo.data.model.TimeLineData
import com.tico.pomorodo.data.model.TimeLineType
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.ui.common.view.SimpleDropDownMoreInfo
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.TodoItem
import com.tico.pomorodo.ui.common.view.TodoListDialog
import com.tico.pomorodo.ui.common.view.getTimeFormat
import com.tico.pomorodo.ui.history.viewmodel.HistoryViewModel
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcTimelineOrangeFilled
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcTimelineOrangeOutline
import com.tico.pomorodo.ui.theme.IC_TIMELINE_MORE_INFO
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

@Composable
fun HistoryRoute(viewModel: HistoryViewModel = hiltViewModel(), navigateToBack: () -> Unit) {
    val timeLine by viewModel.timeLine.collectAsState()
    val todoList by viewModel.todoList.collectAsState()
    val selectedDay by remember { mutableStateOf(LocalDate.now()) }
    var historyDeleteDialogVisible by rememberSaveable { mutableStateOf(false) }
    var historyEditDialogVisible by rememberSaveable { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = PomoroDoTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            HistoryTopBar(
                titleTextId = R.string.title_history,
                onBackClickedListener = navigateToBack
            )
            HistoryScreen(
                selectedDay = selectedDay,
                timeLine = timeLine,
                onMoreInfoEditClicked = {
                    historyEditDialogVisible = true
                    viewModel.setDialogSelectedIndex(it)
                },
                onMoreInfoDeleteClicked = { historyDeleteDialogVisible = true }
            )
            if (historyDeleteDialogVisible) {
                HistoryDeleteDialog(
                    onConfirmation = { },
                    onDismissRequest = { historyDeleteDialogVisible = false }
                )
            }
            if (historyEditDialogVisible) {
                TodoListDialog(
                    title = stringResource(id = R.string.content_todo_edit),
                    todoList = todoList,
                    confirmTextId = R.string.content_todo_more_info_edit,
                    onConfirmation = {
                        viewModel.setTodoEdit(it)
                        historyEditDialogVisible = false
                    },
                    onDismissRequest = { historyEditDialogVisible = false },
                )
            }
        }
    }
}

@Composable
fun HistoryScreen(
    selectedDay: LocalDate, timeLine: List<TimeLineData>,
    onMoreInfoEditClicked: (Int) -> Unit,
    onMoreInfoDeleteClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier,
        color = PomoroDoTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 18.dp, end = 18.dp, bottom = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            TotalHistory(
                month = selectedDay.monthValue,
                day = selectedDay.dayOfMonth,
                dayOfWeek = DayOfWeeks.entries[selectedDay.dayOfWeek.value % 7].dayId,
                concentrationHour = 1,
                concentrationMin = 2,
                concentrationSec = 12,
                breakHour = 3,
                breakMin = 4,
                breakSec = 5,
                concentrationMaxHour = 6,
                concentrationMaxMin = 7,
                concentrationMaxSec = 8,
                breakMaxHour = 9,
                breakMaxMin = 10,
                breakMaxSec = 11,
                targetHour = 3,
                targetMin = 2,
                targetSec = 5
            )
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            TimeLine(
                timeLine = timeLine,
                onMoreInfoEditClicked = onMoreInfoEditClicked,
                onMoreInfoDeleteClicked = onMoreInfoDeleteClicked
            )
        }
    }
}

@Composable
fun TotalHistory(
    month: Int,
    day: Int,
    @StringRes
    dayOfWeek: Int,
    concentrationHour: Int,
    concentrationMin: Int,
    concentrationSec: Int,
    targetHour: Int,
    targetMin: Int,
    targetSec: Int,
    breakHour: Int,
    breakMin: Int,
    breakSec: Int,
    concentrationMaxHour: Int,
    concentrationMaxMin: Int,
    concentrationMaxSec: Int,
    breakMaxHour: Int,
    breakMaxMin: Int,
    breakMaxSec: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                PomoroDoTheme.colorScheme.surfaceContainerLow,
                RoundedCornerShape(15.dp)
            )
            .padding(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleText(
            text = stringResource(
                id = R.string.title_today,
                month,
                day,
                stringResource(id = dayOfWeek)
            ),
            style = PomoroDoTheme.typography.laundryGothicBold18,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SimpleText(
                textId = R.string.title_taget_concentration,
                style = PomoroDoTheme.typography.laundryGothicRegular16,
                color = PomoroDoTheme.colorScheme.primary
            )
            SimpleText(
                text = String.format(
                    Locale.KOREA,
                    stringResource(id = R.string.format_hour_minute_second),
                    targetHour,
                    targetMin,
                    targetSec
                ),
                color = PomoroDoTheme.colorScheme.primary,
                style = PomoroDoTheme.typography.laundryGothicRegular16
            )
        }
        Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(70.dp)) {
            HistoryTimeText(
                title = stringResource(id = R.string.title_total_concentration),
                hour = concentrationHour,
                minute = concentrationMin,
                second = concentrationSec,
            )
            HistoryTimeText(
                title = stringResource(id = R.string.title_max_concentration),
                hour = concentrationMaxHour,
                minute = concentrationMaxMin,
                second = concentrationMaxSec,
            )
        }
        Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(70.dp)) {
            HistoryTimeText(
                title = stringResource(id = R.string.title_total_break),
                hour = breakHour,
                minute = breakMin,
                second = breakSec,
            )
            HistoryTimeText(
                title = stringResource(id = R.string.title_max_break),
                hour = breakMaxHour,
                minute = breakMaxMin,
                second = breakMaxSec,
            )
        }
    }
}

@Composable
fun HistoryTimeText(
    title: String,
    hour: Int,
    minute: Int,
    second: Int,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleText(
            text = title,
            color = PomoroDoTheme.colorScheme.primaryContainer,
            textAlign = TextAlign.Center,
            style = PomoroDoTheme.typography.laundryGothicRegular14
        )
        Spacer(modifier = Modifier.height(5.dp))
        SimpleText(
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute_second),
                hour,
                minute,
                second
            ),
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = PomoroDoTheme.typography.laundryGothicRegular12
        )
    }
}

@Composable
fun TimeLine(
    timeLine: List<TimeLineData>,
    onMoreInfoEditClicked: (Int) -> Unit,
    onMoreInfoDeleteClicked: () -> Unit,
) {
    SimpleText(
        textId = R.string.title_timeline,
        style = PomoroDoTheme.typography.laundryGothicBold20,
        color = PomoroDoTheme.colorScheme.onBackground
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .height(IntrinsicSize.Min)
    ) {
        VerticalDottedLine(x = 82, color = PomoroDoTheme.colorScheme.primary)
        Column(
            horizontalAlignment = Alignment.End
        ) {
            timeLine.forEachIndexed { index, item ->
                when (item.type) {
                    TimeLineType.OUTSIDE -> {
                        if (index != 0) {
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Spacer(modifier = Modifier.width(60.dp))
                            OutsideItem(
                                modifier = Modifier,
                                start = item.start,
                                end = item.end
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    TimeLineType.FOCUS, TimeLineType.BREAK -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                        ) {
                            SimpleText(
                                modifier = Modifier
                                    .width(60.dp)
                                    .offset(y = 10.dp),
                                text = item.start.getTimeFormat(),
                                style = PomoroDoTheme.typography.laundryGothicRegular10,
                                color = PomoroDoTheme.colorScheme.outline
                            )
                            TimeLineItem(
                                modifier = Modifier,
                                start = item.start,
                                end = item.end,
                                isBreak = item.type == TimeLineType.BREAK,
                                todoList = item.list,
                                onMoreInfoEditClicked = { onMoreInfoEditClicked(index) },
                                onMoreInfoDeleteClicked = onMoreInfoDeleteClicked
                            )
                        }
                        if (index != timeLine.size - 1) {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OutsideItem(modifier: Modifier = Modifier, start: LocalDateTime, end: LocalDateTime) {
    val startTime = start.getTimeFormat()
    val endTime = end.getTimeFormat()
    val duration = Duration.between(start, end)

    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    val formattedDuration = if (hours > 0) {
        stringResource(
            id = R.string.content_timeline_hour_minute_second,
            hours,
            minutes,
            seconds
        )
    } else {
        stringResource(id = R.string.content_timeline_minute_second, minutes, seconds)
    }
    Row() {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    PomoroDoTheme.colorScheme.outline,
                    RoundedCornerShape(5.dp)
                )
                .background(PomoroDoTheme.colorScheme.gray90, RoundedCornerShape(5.dp))
                .padding(start = 30.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleText(
                text = startTime,
                style = PomoroDoTheme.typography.laundryGothicRegular10,
                color = PomoroDoTheme.colorScheme.outline
            )
            SimpleText(
                textId = R.string.content_wave,
                style = PomoroDoTheme.typography.laundryGothicRegular10,
                color = PomoroDoTheme.colorScheme.outline
            )
            SimpleText(
                text = endTime,
                style = PomoroDoTheme.typography.laundryGothicRegular10,
                color = PomoroDoTheme.colorScheme.outline
            )
            SimpleText(
                text = formattedDuration,
                style = PomoroDoTheme.typography.laundryGothicRegular10,
                color = PomoroDoTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun TimeLineItem(
    modifier: Modifier = Modifier,
    start: LocalDateTime,
    end: LocalDateTime,
    isBreak: Boolean,
    todoList: List<TodoData>? = null,
    onMoreInfoEditClicked: () -> Unit,
    onMoreInfoDeleteClicked: () -> Unit,
) {
    val startTime = start.getTimeFormat()
    val endTime = end.getTimeFormat()
    val duration = Duration.between(start, end)

    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    val formattedDuration = if (hours > 0) {
        stringResource(
            id = R.string.content_timeline_hour_minute_second,
            hours,
            minutes,
            seconds
        )
    } else {
        stringResource(id = R.string.content_timeline_minute_second, minutes, seconds)
    }
    var showMoreInfo by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .border(
                1.dp,
                PomoroDoTheme.colorScheme.outline,
                RoundedCornerShape(5.dp)
            )
            .background(PomoroDoTheme.colorScheme.background)
            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp, start = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.Top
    ) {
        SimpleIcon(
            size = 16,
            imageVector = if (isBreak) IconPack.IcTimelineOrangeOutline else IconPack.IcTimelineOrangeFilled,
            contentDescriptionId = if (isBreak) R.string.content_ic_time_line_break else R.string.content_ic_time_line_concentration
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SimpleText(
                text = formattedDuration,
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleText(
                    text = startTime,
                    style = PomoroDoTheme.typography.laundryGothicRegular10,
                    color = PomoroDoTheme.colorScheme.outline
                )
                SimpleText(
                    textId = R.string.content_wave,
                    style = PomoroDoTheme.typography.laundryGothicRegular10,
                    color = PomoroDoTheme.colorScheme.outline
                )
                SimpleText(
                    text = endTime,
                    style = PomoroDoTheme.typography.laundryGothicRegular10,
                    color = PomoroDoTheme.colorScheme.outline
                )
            }
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            if (isBreak) {
                SimpleText(
                    textId = R.string.content_break,
                    style = PomoroDoTheme.typography.laundryGothicRegular12,
                    color = PomoroDoTheme.colorScheme.outline
                )
            } else {
                todoList?.forEach {
                    TodoItem(
                        iconSize = 16,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular12,
                        todoData = it,
                        enabled = false
                    )
                }
            }
        }
        Column {
            SimpleIconButton(
                size = 18,
                imageVector = PomoroDoTheme.iconPack[IC_TIMELINE_MORE_INFO]!!,
                contentDescriptionId = R.string.content_ic_more_info,
                onClickedListener = { showMoreInfo = true },
                enabled = true
            )
            SimpleDropDownMoreInfo(
                showMoreInfo = showMoreInfo,
                onShowMoreInfoChange = { showMoreInfo = it },
                editTextId = R.string.content_todo_edit,
                deleteTextId = R.string.content_history_delete,
                onMoreInfoEditClicked = onMoreInfoEditClicked,
                onMoreInfoDeleteClicked = onMoreInfoDeleteClicked,
                paddingValues = PaddingValues(vertical = 5.dp, horizontal = 20.dp)
            )
        }
    }
}

@Composable
private fun VerticalDottedLine(x: Int, color: Color) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
    Canvas(
        Modifier
            .fillMaxHeight()
            .height(1.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(x.dp.toPx(), 0f),
            end = Offset(x.dp.toPx(), size.height),
            pathEffect = pathEffect,
            strokeWidth = 1.dp.toPx()
        )
    }
}
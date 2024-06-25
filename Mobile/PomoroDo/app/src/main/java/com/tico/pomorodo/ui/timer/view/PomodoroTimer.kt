package com.tico.pomorodo.ui.timer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun CustomPomodoroTimer(
    concentrationTime: Time,
    breakTime: Time,
    onConcentrationTimeChange: (Int) -> Unit,
    onBreakTimeChange: (Int) -> Unit,
    isEditTimerDialogVisible: Boolean,
    onConcentrationTimeClick: () -> Unit,
    onBreakTimeClick: () -> Unit,
) {
    val initialConcentrationTime = concentrationTime.hour * 60 + concentrationTime.minute
    val initialBreakTime = breakTime.hour * 60 + breakTime.minute
    val concentrationColor = PomoroDoTheme.colorScheme.primaryContainer
    val breakColor =
        if (isSystemInDarkTheme()) PomoroDoTheme.colorScheme.secondary
        else PomoroDoTheme.colorScheme.secondaryContainer
    val backgroundColor =
        if (isSystemInDarkTheme()) PomoroDoTheme.colorScheme.gray70
        else PomoroDoTheme.colorScheme.background

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditableTextTimer(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            concentrationColor = concentrationColor,
            breakColor = breakColor,
            onConcentrationTimeClick = onConcentrationTimeClick,
            onBreakTimeClick = onBreakTimeClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomCircularDraggableTimer(
            modifier = Modifier
                .size(300.dp)
                .background(PomoroDoTheme.colorScheme.background),
            outerTimerColor = concentrationColor,
            innerTimerColor = breakColor,
            backgroundColor = backgroundColor,
            indicatorColor = PomoroDoTheme.colorScheme.onBackground,
            outerCircleRadius = 125,
            outerInitialValue = initialConcentrationTime,
            innerInitialValue = initialBreakTime,
            onOuterPositionChange = onConcentrationTimeChange,
            onInnerPositionChange = onBreakTimeChange,
            isEditTimerDialogVisible = isEditTimerDialogVisible
        )
    }
}

@Composable
fun EditableTextTimer(
    concentrationTime: Time,
    breakTime: Time,
    concentrationColor: Color,
    breakColor: Color,
    onConcentrationTimeClick: () -> Unit,
    onBreakTimeClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomTimeText(
            title = stringResource(R.string.title_concentration_time),
            hour = concentrationTime.hour,
            minute = concentrationTime.minute,
            textColor = concentrationColor,
            spaceDp = 10.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular16,
            onClick = onConcentrationTimeClick
        )

        Spacer(modifier = Modifier.width(30.dp))

        CustomTimeText(
            title = stringResource(R.string.title_break_time),
            hour = breakTime.hour,
            minute = breakTime.minute,
            textColor = breakColor,
            spaceDp = 10.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular16,
            onClick = onBreakTimeClick
        )
    }
}
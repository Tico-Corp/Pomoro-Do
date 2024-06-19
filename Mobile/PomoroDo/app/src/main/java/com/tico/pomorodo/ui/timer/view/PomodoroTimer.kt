package com.tico.pomorodo.ui.timer.view

import androidx.compose.foundation.background
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
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun CustomPomodoroTimer(
    concentrationTime: Int,
    breakTime: Int,
    onConcentrationTimeChange: (Int) -> Unit,
    onBreakTimeChange: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditableTextTimer(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            concentrationColor = PomoroDoTheme.colorScheme.primaryContainer,
            breakColor = PomoroDoTheme.colorScheme.secondaryContainer
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomCircularDraggableTimer(
            modifier = Modifier
                .size(300.dp)
                .background(PomoroDoTheme.colorScheme.background),
            outerTimerColor = PomoroDoTheme.colorScheme.primaryContainer,
            innerTimerColor = PomoroDoTheme.colorScheme.secondaryContainer,
            backgroundColor = PomoroDoTheme.colorScheme.background,
            indicatorColor = PomoroDoTheme.colorScheme.onBackground,
            outerCircleRadius = 125,
            outerInitialValue = concentrationTime,
            innerInitialValue = breakTime,
            onOuterPositionChange = { position -> onConcentrationTimeChange(position) },
            onInnerPositionChange = { position -> onBreakTimeChange(position) }
        )
    }
}

@Composable
fun EditableTextTimer(
    concentrationTime: Int,
    breakTime: Int,
    concentrationColor: Color,
    breakColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomTimeText(
            title = stringResource(R.string.title_concentration_time),
            hour = concentrationTime / 60,
            minute = concentrationTime % 60,
            textColor = concentrationColor,
            spaceDp = 10.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular16
        ) { /*TODO*/ }

        Spacer(modifier = Modifier.width(20.dp))

        CustomTimeText(
            title = stringResource(R.string.title_break_time),
            hour = breakTime / 60,
            minute = breakTime % 60,
            textColor = breakColor,
            spaceDp = 10.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular16
        ) { /*TODO*/ }
    }
}
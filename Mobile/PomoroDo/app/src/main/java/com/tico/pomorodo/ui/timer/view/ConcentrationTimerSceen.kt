package com.tico.pomorodo.ui.timer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.theme.palettesNeutral20
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

@Composable
fun ConcentrationTimerScreen(
    timerViewModel: TimerViewModel,
) {
    val concentrationTime by timerViewModel.concentrationTime.collectAsState()
    val timerTextColor =
        if (isSystemInDarkTheme()) PomoroDoTheme.colorScheme.onBackground
        else PomoroDoTheme.colorScheme.background
    val buttonTextColor =
        if (isSystemInDarkTheme()) PomoroDoTheme.colorScheme.onBackground
        else PomoroDoTheme.colorScheme.onPrimary
    val backgroundColor =
        if (isSystemInDarkTheme()) PomoroDoTheme.colorScheme.background
        else palettesNeutral20
    val timerBackgroundColor =
        if (isSystemInDarkTheme()) PomoroDoTheme.colorScheme.gray70
        else PomoroDoTheme.colorScheme.onPrimary
    val timeToSecond = concentrationTime.hour * 60 * 60 +
            concentrationTime.minute * 60 +
            (concentrationTime.second ?: 0)
    val maxValue by remember {
        mutableIntStateOf(timeToSecond)
    }

    Column(
        modifier = Modifier
            .background(backgroundColor)
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTimeText(
            title = "집중 시간",
            hour = concentrationTime.hour,
            minute = concentrationTime.minute,
            second = concentrationTime.second ?: 0,
            textColor = timerTextColor,
            spaceDp = 6.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular26
        )

        Spacer(modifier = Modifier.height(36.dp))

        CustomCircularTimer(
            modifier = Modifier.size(300.dp),
            timerColor = PomoroDoTheme.colorScheme.primaryContainer,
            backgroundColor = timerBackgroundColor,
            circleRadius = 125,
            maxValue = maxValue,
            initialValue = timeToSecond,
        )

        Spacer(modifier = Modifier.height(100.dp))

        CustomTextButton(
            text = "집중 종료",
            backgroundColor = PomoroDoTheme.colorScheme.primaryContainer,
            textColor = buttonTextColor,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp
        ) { /*TODO*/ }
    }
}

@Preview
@Composable
fun Preview() {
    PomoroDoTheme {
        ConcentrationTimerScreen(timerViewModel = viewModel())
    }
}
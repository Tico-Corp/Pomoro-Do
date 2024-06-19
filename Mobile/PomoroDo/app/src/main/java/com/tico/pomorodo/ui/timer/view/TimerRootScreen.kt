package com.tico.pomorodo.ui.timer.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun TimerRootScreen() {
    var concentrationTime by remember {
        mutableIntStateOf(30)
    }
    var breakTime by remember {
        mutableIntStateOf(70)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PomodoroTimerScreen(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            onConcentrationTimeChange = { position -> concentrationTime = position },
            onBreakTimeChange = { position -> breakTime = position }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextButton(
            text = stringResource(R.string.content_button_start),
            backgroundColor = PomoroDoTheme.colorScheme.primaryContainer,
            textColor = PomoroDoTheme.colorScheme.background,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp
        ) { /*TODO*/ }
    }
}

@Composable
fun PomodoroTimerScreen(
    concentrationTime: Int,
    breakTime: Int,
    onConcentrationTimeChange: (Int) -> Unit,
    onBreakTimeChange: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomPomodoroTimer(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            onConcentrationTimeChange = onConcentrationTimeChange,
            onBreakTimeChange = onBreakTimeChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        TodayConcentrationInformation()
    }
}

@Composable
fun TodayConcentrationInformation() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTimeText(
            title = stringResource(R.string.title_taget_concentration),
            hour = 12,
            minute = 0,
            second = 0,
            textColor = PomoroDoTheme.colorScheme.onBackground,
            spaceDp = 4.dp,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTimeText(
                title = stringResource(R.string.title_total_concentration),
                hour = 10,
                minute = 0,
                second = 0,
                textColor = PomoroDoTheme.colorScheme.onBackground,
                spaceDp = 4.dp,
                textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            )

            Spacer(modifier = Modifier.width(50.dp))

            CustomTimeText(
                title = stringResource(R.string.title_total_break),
                hour = 3,
                minute = 0,
                second = 0,
                textColor = PomoroDoTheme.colorScheme.onBackground,
                spaceDp = 4.dp,
                textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            )
        }
    }
}
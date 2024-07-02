package com.tico.pomorodo.ui.timer.running.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.ui.common.view.CONCENTRATION_TIME
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.common.view.SimpleAlertDialog
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.running.viewmodel.TimerRunningViewModel
import com.tico.pomorodo.ui.timer.setup.view.CustomCircularTimer
import kotlinx.coroutines.delay

@Composable
fun ConcentrationTimerScreen(
    timerRunningViewModel: TimerRunningViewModel = hiltViewModel(),
    getState: (String) -> Int?
) {
    LaunchedEffect(key1 = Unit) {
        timerRunningViewModel.initialConcentrationTime(getState(CONCENTRATION_TIME) ?: 0)
    }

    val concentrationTime by timerRunningViewModel.concentrationTime.collectAsState()
    val maxValue by timerRunningViewModel.timerMaxValue.collectAsState()
    val (isFinished, setFinish) = remember { mutableStateOf(false) }
    val (isPaused, setPause) = remember { mutableStateOf(false) }
    val (finishTimerDialogVisible, setFinishTimerDialogVisible) = remember {
        mutableStateOf(false)
    }
    var second by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = second, key2 = isPaused) {
        if (!isPaused) {
            delay(1000)

            second = updateTimer(
                time = concentrationTime.copy(),
                onFinishedChange = { setFinish(true) },
                onTimeChanged = timerRunningViewModel::setConcentrationTime
            )
        }
    }

    TimerScreenLayout(concentrationTime = concentrationTime, maxValue = maxValue) {
        setPause(true)
        setFinishTimerDialogVisible(true)
    }

    if (finishTimerDialogVisible) {
        FinishTimerDialog(
            onConfirmation = { setFinishTimerDialogVisible(false) },
            onDismissRequest = {
                setPause(false)
                setFinishTimerDialogVisible(false)
            }
        )
    }
}

@Composable
fun TimerScreenLayout(concentrationTime: Time, maxValue: Int, onClick: () -> Unit) {
    var hour by remember { mutableIntStateOf(concentrationTime.hour) }
    var minute by remember { mutableIntStateOf(concentrationTime.minute) }
    val second by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        hour = concentrationTime.hour
        minute = concentrationTime.minute
    }

    val timeToSecond = hour * 60 * 60 + minute * 60 + second

    Column(
        modifier = Modifier
            .background(PomoroDoTheme.colorScheme.modeBackgroundColor)
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTimeText(
            title = stringResource(R.string.title_concentration_time_left),
            hour = concentrationTime.hour,
            minute = concentrationTime.minute,
            second = concentrationTime.second ?: 0,
            textColor = Color.White,
            spaceDp = 6.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular26
        )

        Spacer(modifier = Modifier.height(36.dp))

        CustomCircularTimer(
            modifier = Modifier.size(300.dp),
            timerColor = PomoroDoTheme.colorScheme.primaryContainer,
            backgroundColor = PomoroDoTheme.colorScheme.timerBackgroundColor,
            circleRadius = 125,
            maxValue = maxValue,
            initialValue = timeToSecond,
        )

        Spacer(modifier = Modifier.height(100.dp))

        CustomTextButton(
            text = stringResource(R.string.content_button_finish_concentration),
            containerColor = PomoroDoTheme.colorScheme.primaryContainer,
            contentColor = Color.White,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp,
            onClick = onClick
        )
    }
}

private fun updateTimer(
    time: Time,
    onFinishedChange: () -> Unit,
    onTimeChanged: (Int, Int, Int) -> Unit,
): Int {
    if (time.second != 0) {
        time.second = time.second!! - 1
    } else {
        if (time.minute != 0) {
            time.minute--
            time.second = 59
        } else {
            if (time.hour != 0) {
                time.hour--
                time.minute = 59
                time.second = 59
            } else {
                onFinishedChange()
            }
        }
    }

    onTimeChanged(time.hour, time.minute, time.second!!)

    return time.second!!
}

@Composable
fun FinishTimerDialog(onConfirmation: () -> Unit, onDismissRequest: () -> Unit) {
    SimpleAlertDialog(
        dialogTitleId = R.string.title_finish_concentration,
        confirmTextId = R.string.content_finish,
        dismissTextId = R.string.content_cancel,
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest,
    ) {
        Text(
            text = stringResource(R.string.content_finish_concentration),
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = PomoroDoTheme.typography.laundryGothicRegular14
        )
    }
}
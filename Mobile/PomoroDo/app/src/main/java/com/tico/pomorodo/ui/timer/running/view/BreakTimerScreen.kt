package com.tico.pomorodo.ui.timer.running.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.running.viewmodel.TimerRunningViewModel
import kotlinx.coroutines.delay

@Composable
fun BreakTimerScreen(navBackStackEntry: NavBackStackEntry) {
    val timerRunningViewModel: TimerRunningViewModel = hiltViewModel(navBackStackEntry)

    LaunchedEffect(key1 = Unit) {
        timerRunningViewModel.initialBreakTime()
    }

    val breakTime by timerRunningViewModel.breakTime.collectAsState()
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
                time = breakTime.copy(),
                onFinishedChange = { setFinish(true) },
                onTimeChanged = timerRunningViewModel::setBreakTime
            )
        }
    }

    TimerScreenLayout(
        title = stringResource(id = R.string.title_break_time),
        time = breakTime,
        maxValue = maxValue,
        timerColor = PomoroDoTheme.colorScheme.breakTimeColor,
        buttonContent = stringResource(id = R.string.content_button_finish_break)
    ) {
        setPause(true)
        setFinishTimerDialogVisible(true)
    }

    if (finishTimerDialogVisible) {
        FinishTimerDialog(
            titleId = R.string.title_finish_break,
            contentId = R.string.content_finish_break,
            onConfirmation = {
                setFinish(true)
                setFinishTimerDialogVisible(false)
            },
            onDismissRequest = {
                setPause(false)
                setFinishTimerDialogVisible(false)
            }
        )
    }
}
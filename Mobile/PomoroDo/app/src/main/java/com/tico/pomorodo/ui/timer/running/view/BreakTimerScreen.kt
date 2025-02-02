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
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.BREAK_TIME
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.running.viewmodel.BreakTimeViewModel
import kotlinx.coroutines.delay

@Composable
fun BreakTimerScreen(navigateToTimerHome: () -> Unit, getState: (String) -> Int?) {
    val breakTimeViewModel: BreakTimeViewModel = hiltViewModel()

    LaunchedEffect(key1 = Unit) {
        breakTimeViewModel.initialBreakTime(getState(BREAK_TIME) ?: 0)
    }

    val breakTime by breakTimeViewModel.breakTime.collectAsState()
    val maxValue by breakTimeViewModel.timerMaxValue.collectAsState()
    val (isFinished, setFinish) = remember { mutableStateOf(false) }
    val (isPaused, setPause) = remember { mutableStateOf(false) }
    val (finishTimerDialogVisible, setFinishTimerDialogVisible) = remember {
        mutableStateOf(false)
    }
    var second by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = second, key2 = isPaused, key3 = isFinished) {
        if (!isPaused) {
            delay(1000)

            second = updateTimer(
                time = breakTime.copy(),
                onFinishedChange = { setFinish(true) },
                onTimeChanged = breakTimeViewModel::setBreakTime
            )
        }

        if (isFinished) {
            navigateToTimerHome()
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
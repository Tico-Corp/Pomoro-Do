package com.tico.pomorodo.ui.timer.view

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
import androidx.navigation.NavController
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.common.view.SimpleAlertDialog
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel
import kotlinx.coroutines.delay

@Composable
fun ConcentrationTimerScreen(
    navController: NavController,
    timerViewModel: TimerViewModel,
) {
    val concentrationTime by timerViewModel.concentrationTime.collectAsState()
    val maxValue by timerViewModel.timerMaxValue.collectAsState()
    var hour by remember { mutableIntStateOf(concentrationTime.hour) }
    var minute by remember { mutableIntStateOf(concentrationTime.minute) }
    var second by remember { mutableIntStateOf(concentrationTime.second ?: 0) }
    val timeToSecond = hour * 60 * 60 + minute * 60 + second
    var isFinished by remember { mutableStateOf(false) }
    val (isPaused, setPause) = remember { mutableStateOf(false) }
    val (finishTimerDialogVisible, setFinishTimerDialogVisible) = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = second, key2 = isPaused) {
        if (!isPaused) {
            delay(1000)

            if (second != 0) {
                second--
            } else {
                if (minute != 0) {
                    minute--
                    second = 59
                } else {
                    if (hour != 0) {
                        hour--
                        minute = 59
                        second = 59
                    } else {
                        isFinished = true
                    }
                }
            }

            timerViewModel.setConcentrationTime(hour, minute, second)
        }
    }

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
            backgroundColor = PomoroDoTheme.colorScheme.primaryContainer,
            textColor = Color.White,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp
        ) {
            setPause(true)
            setFinishTimerDialogVisible(true)
        }
    }

    if (finishTimerDialogVisible)
        SimpleAlertDialog(
            dialogTitleId = R.string.title_finish_concentration,
            confirmTextId = R.string.content_finish,
            dismissTextId = R.string.content_cancel,
            onConfirmation = {
                /*TODO*/
                setFinishTimerDialogVisible(false)
            },
            onDismissRequest = {
                setPause(false)
                setFinishTimerDialogVisible(false)
            },
        ) {
            Text(
                text = stringResource(R.string.content_finish_concentration),
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = PomoroDoTheme.typography.laundryGothicRegular14
            )
        }
}
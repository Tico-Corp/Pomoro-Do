package com.tico.pomorodo.ui.timer.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

@Preview
@Composable
fun TimerRootScreen() {
    val timerViewModel: TimerViewModel = viewModel()
    val concentrationTime by timerViewModel.concentrationTime.collectAsState()
    val breakTime by timerViewModel.breakTime.collectAsState()
    val concentrationGoal by timerViewModel.concentrationGoal.collectAsState()
    val (editConcentrationTimerDialogVisible, setEditConcentrationTimerDialogVisible) = remember {
        mutableStateOf(false)
    }
    val (editBreakTimerDialogVisible, setEditBreakTimerDialogVisible) = remember {
        mutableStateOf(false)
    }
    val (editConcentrationGoalDialogVisible, setEditConcentrationGoalDialogVisible) = remember {
        mutableStateOf(false)
    }
    val buttonTextColor =
        if (isSystemInDarkTheme()) PomoroDoTheme.colorScheme.onBackground
        else PomoroDoTheme.colorScheme.onPrimary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PomodoroTimerScreen(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            concentrationGoal = concentrationGoal,
            onConcentrationTimeChange = { time ->
                timerViewModel.setConcentrationTime(
                    hour = time / 60,
                    minute = time % 60
                )
            },
            onBreakTimeChange = { time ->
                timerViewModel.setBreakTime(
                    hour = time / 60,
                    minute = time % 60
                )
            },
            isEditTimerDialogVisible = editConcentrationTimerDialogVisible || editBreakTimerDialogVisible,
            onConcentrationTimeClick = { setEditConcentrationTimerDialogVisible(true) },
            onBreakTimeClick = { setEditBreakTimerDialogVisible(true) },
            onConcentrationGoalClick = { setEditConcentrationGoalDialogVisible(true) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextButton(
            text = stringResource(R.string.content_button_start),
            backgroundColor = PomoroDoTheme.colorScheme.primaryContainer,
            textColor = buttonTextColor,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp
        ) { /*TODO*/ }
    }

    if (editConcentrationTimerDialogVisible) {
        EditTimerDialog(
            title = stringResource(R.string.title_dialog_edit_concentration_time),
            initialValue = concentrationTime,
            onDismissRequest = {
                setEditConcentrationTimerDialogVisible(false)
            },
            onConfirmation = { hour, minute, _ ->
                timerViewModel.setConcentrationTime(hour, minute)
                setEditConcentrationTimerDialogVisible(false)
            }
        )
    }

    if (editBreakTimerDialogVisible) {
        EditTimerDialog(
            title = stringResource(R.string.title_dialog_edit_break_time),
            initialValue = breakTime,
            onDismissRequest = {
                setEditBreakTimerDialogVisible(false)
            },
            onConfirmation = { hour, minute, _ ->
                timerViewModel.setBreakTime(hour, minute)
                setEditBreakTimerDialogVisible(false)
            }
        )
    }

    if (editConcentrationGoalDialogVisible) {
        EditTimerDialog(
            title = stringResource(R.string.title_concentration_goal_dialog),
            initialValue = concentrationGoal,
            onDismissRequest = {
                setEditConcentrationGoalDialogVisible(false)
            },
            onConfirmation = { hour, minute, second ->
                timerViewModel.setConcentrationGoal(hour, minute, second!!)
                setEditConcentrationGoalDialogVisible(false)
            }
        )

    }
}

@Composable
fun PomodoroTimerScreen(
    concentrationTime: Time,
    breakTime: Time,
    concentrationGoal: Time,
    onConcentrationTimeChange: (Int) -> Unit,
    onBreakTimeChange: (Int) -> Unit,
    isEditTimerDialogVisible: Boolean,
    onConcentrationTimeClick: () -> Unit,
    onBreakTimeClick: () -> Unit,
    onConcentrationGoalClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomPomodoroTimer(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            onConcentrationTimeChange = onConcentrationTimeChange,
            onBreakTimeChange = onBreakTimeChange,
            isEditTimerDialogVisible = isEditTimerDialogVisible,
            onConcentrationTimeClick = onConcentrationTimeClick,
            onBreakTimeClick = onBreakTimeClick,
        )

        Spacer(modifier = Modifier.height(24.dp))

        TodayConcentrationInformation(
            concentrationGoal = concentrationGoal,
            onConcentrationGoalClick = onConcentrationGoalClick
        )
    }
}

@Composable
fun TodayConcentrationInformation(
    concentrationGoal: Time,
    onConcentrationGoalClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTimeText(
            title = stringResource(R.string.title_taget_concentration),
            hour = concentrationGoal.hour,
            minute = concentrationGoal.minute,
            second = concentrationGoal.second!!,
            textColor = PomoroDoTheme.colorScheme.onBackground,
            spaceDp = 4.dp,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            onClick = onConcentrationGoalClick
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
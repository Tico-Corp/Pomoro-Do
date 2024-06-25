package com.tico.pomorodo.ui.timer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.WheelTimePicker
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun EditTimerDialog(
    title: String,
    initialValue: Time,
    onDismissRequest: () -> Unit,
    onConfirmation: (hour: Int, minute: Int, second: Int?) -> Unit,
) {
    var currentHour by remember { mutableIntStateOf(initialValue.hour) }
    var currentMinute by remember { mutableIntStateOf(initialValue.minute) }
    var currentSecond by remember { mutableStateOf(initialValue.second) }
    val contentPadding = if (currentSecond == null) 20 else 12
    var isTimeValid by remember { mutableStateOf(true) }
    val timeLimit = if (currentSecond == null) 2 else 24

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = PomoroDoTheme.colorScheme.dialogSurface)
                .padding(horizontal = 30.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = PomoroDoTheme.colorScheme.onBackground,
                style = PomoroDoTheme.typography.laundryGothicRegular20
            )

            Spacer(modifier = Modifier.height(16.dp))

            WheelTimePicker(
                initialHour = initialValue.hour,
                initialMinute = initialValue.minute,
                initialSecond = initialValue.second,
                contentPadding = contentPadding.dp,
                onHourChanged = { hour ->
                    currentHour = hour
                    isTimeValid =
                        if (currentSecond == null && currentHour * 60 + currentMinute <= 120) true
                        else if (currentSecond != null && currentHour * 60 * 60 + currentMinute * 60 + currentSecond!! <= 86400) true
                        else false
                },
                onMinuteChanged = { minute ->
                    currentMinute = minute
                    isTimeValid =
                        if (currentSecond == null && currentHour * 60 + currentMinute <= 120) true
                        else if (currentSecond != null && currentHour * 60 * 60 + currentMinute * 60 + currentSecond!! <= 86400) true
                        else false
                },
                onSecondChanged = { second ->
                    currentSecond = second
                    isTimeValid =
                        currentHour * 60 * 60 + currentMinute * 60 + currentSecond!! <= 86400
                }
            )


            Box(modifier = Modifier.height(32.dp), contentAlignment = Alignment.Center) {
                if (!isTimeValid) {
                    Text(
                        text = String.format(
                            stringResource(R.string.content_error_time_limit),
                            timeLimit
                        ),
                        color = PomoroDoTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        style = PomoroDoTheme.typography.laundryGothicRegular12
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTextButton(
                    text = stringResource(id = R.string.content_cancel),
                    backgroundColor = Color.Unspecified,
                    textColor = PomoroDoTheme.colorScheme.onBackground,
                    textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                    horizontalPadding = 20.dp,
                    verticalPadding = 8.dp
                ) { onDismissRequest() }

                Spacer(modifier = Modifier.width(10.dp))

                CustomTextButton(
                    text = stringResource(id = R.string.content_ok),
                    enable = isTimeValid,
                    containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                    contentColor = Color.White,
                    disabledContainerColor = PomoroDoTheme.colorScheme.gray70,
                    disabledContentColor = Color.White,
                    textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                    horizontalPadding = 20.dp,
                    verticalPadding = 8.dp
                ) {
                    onConfirmation(currentHour, currentMinute, currentSecond)
                }
            }
        }
    }
}
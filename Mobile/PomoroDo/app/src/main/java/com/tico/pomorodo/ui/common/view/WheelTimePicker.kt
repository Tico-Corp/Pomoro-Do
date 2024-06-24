package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.FWheelPickerFocusVertical
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun WheelTimePicker(
    initialHour: Int,
    initialMinute: Int,
    contentPadding: Dp,
    onHourChanged: (hour: Int) -> Unit,
    onMinuteChanged: (minute: Int) -> Unit
) {
    val pickerTextStyle = PomoroDoTheme.typography.laundryGothicRegular14
    val hour = rememberFWheelPickerState(initialIndex = initialHour)
    val minute = rememberFWheelPickerState(initialIndex = initialMinute)

    Row(
        horizontalArrangement = Arrangement.spacedBy(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FVerticalWheelPicker(
            modifier = Modifier.width(40.dp),
            count = 3,
            state = hour,
            itemHeight = 42.dp,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = PomoroDoTheme.colorScheme.gray30,
                    dividerSize = 1.5.dp
                )
            },
            content = { index ->
                Text(
                    text = index.toString(),
                    color = PomoroDoTheme.colorScheme.onBackground,
                    style = pickerTextStyle
                )
                onHourChanged(hour.currentIndex)
            }
        )

        Text(
            text = ":",
            color = PomoroDoTheme.colorScheme.gray30,
            style = pickerTextStyle
        )

        FVerticalWheelPicker(
            modifier = Modifier.width(40.dp),
            count = 60,
            state = minute,
            itemHeight = 42.dp,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = PomoroDoTheme.colorScheme.gray30,
                    dividerSize = 1.5.dp
                )
            },
            content = { index ->
                Text(
                    text = index.toString(),
                    color = PomoroDoTheme.colorScheme.onBackground,
                    style = pickerTextStyle
                )
                onMinuteChanged(minute.currentIndex)
            }
        )
    }
}
package com.tico.pomorodo.ui.member.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.NoPaddingRadioButton
import com.tico.pomorodo.ui.common.view.clickableWithoutRipple
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingAlarmBottomSheet(
    title: String,
    initialSelect: AlarmOptions,
    onDismissRequest: () -> Unit,
    onConfirmation: (AlarmOptions) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val textColor = PomoroDoTheme.colorScheme.onBackground
    val radioButtonColors = RadioButtonColors(
        selectedColor = PomoroDoTheme.colorScheme.primaryContainer,
        unselectedColor = PomoroDoTheme.colorScheme.dialogGray50,
        disabledSelectedColor = Color.Unspecified,
        disabledUnselectedColor = Color.Unspecified
    )
    val (selectedOption, setSelectedOption) = rememberSaveable { mutableStateOf(initialSelect) }
    val lineSpacer: @Composable () -> Unit = {
        HorizontalDivider(
            thickness = 1.dp,
            color = PomoroDoTheme.colorScheme.dialogGray90
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
        dragHandle = {
            Surface(
                modifier = Modifier.padding(top = 8.dp, bottom = 18.dp),
                color = PomoroDoTheme.colorScheme.dialogGray70,
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Box(Modifier.size(width = 63.dp, height = 3.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = textColor,
                textAlign = TextAlign.Center,
                style = PomoroDoTheme.typography.laundryGothicBold20
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                SettingAlarmBottomSheetItem(
                    modifier = Modifier.padding(bottom = 12.dp),
                    radioButtonColors = radioButtonColors,
                    alarmOptions = AlarmOptions.Sound,
                    selected = (selectedOption == AlarmOptions.Sound),
                    onClick = setSelectedOption
                )

                lineSpacer()

                SettingAlarmBottomSheetItem(
                    modifier = Modifier.padding(vertical = 12.dp),
                    radioButtonColors = radioButtonColors,
                    alarmOptions = AlarmOptions.Vibrate,
                    selected = (selectedOption == AlarmOptions.Vibrate),
                    onClick = setSelectedOption
                )

                lineSpacer()

                SettingAlarmBottomSheetItem(
                    modifier = Modifier.padding(top = 12.dp),
                    radioButtonColors = radioButtonColors,
                    alarmOptions = AlarmOptions.Mute,
                    selected = (selectedOption == AlarmOptions.Mute),
                    onClick = setSelectedOption
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextButton(
                text = stringResource(id = R.string.content_ok),
                containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                contentColor = Color.White,
                textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
                verticalPadding = 12.dp
            ) { onConfirmation(selectedOption) }
        }
    }
}

@Composable
fun SettingAlarmBottomSheetItem(
    modifier: Modifier = Modifier,
    radioButtonColors: RadioButtonColors,
    alarmOptions: AlarmOptions,
    selected: Boolean,
    onClick: (AlarmOptions) -> Unit
) {
    Row(
        modifier = modifier.clickableWithoutRipple { onClick(alarmOptions) },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = PomoroDoTheme.iconPack[alarmOptions.iconName]!!,
            contentDescription = stringResource(id = alarmOptions.title),
            modifier = Modifier.size(16.dp),
            tint = Color.Unspecified,
        )

        Text(
            text = stringResource(id = alarmOptions.title),
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular14
        )

        Spacer(modifier = Modifier.weight(1f))

        NoPaddingRadioButton(
            selected = selected,
            onClick = { onClick(alarmOptions) },
            colors = radioButtonColors
        )
    }
}
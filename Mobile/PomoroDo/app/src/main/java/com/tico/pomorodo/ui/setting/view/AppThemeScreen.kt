package com.tico.pomorodo.ui.setting.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTopAppBar
import com.tico.pomorodo.ui.common.view.NoPaddingRadioButton
import com.tico.pomorodo.ui.common.view.clickableWithRipple
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun AppThemeScreen(initialSelectedMode: String, popBackStack: () -> Unit) {
    var enable by remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf(initialSelectedMode) }

    LaunchedEffect(key1 = selectedMode) {
        if (!enable) {
            enable = true
        } else if (selectedMode == initialSelectedMode) {
            enable = false
        }
    }

    Surface(color = PomoroDoTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomTopAppBar(
                titleTextId = R.string.title_app_theme,
                actionIconString = IC_OK,
                actionDisableIconString = IC_UNOK,
                actionIconDescriptionId = R.string.content_ic_ok,
                isActionEnabled = enable,
                onActionClickedListener = {
                    /*TODO: change app theme*/
                    popBackStack()
                },
                onBackClickedListener = { popBackStack() }
            )

            AppThemeList(
                modeList = appThemeMode,
                selectedMode = selectedMode,
                onSelectedModeChanged = { mode -> selectedMode = mode }
            )
        }
    }
}

@Composable
fun AppThemeList(
    modeList: Map<String, Int>,
    selectedMode: String,
    onSelectedModeChanged: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 18.dp)) {
        modeList.entries.forEachIndexed { index, entry ->
            AppThemeItem(modeResId = entry.value, selected = (selectedMode == entry.key)) {
                onSelectedModeChanged(entry.key)
            }

            if (index != modeList.size - 1)
                HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
        }
    }
}

@Composable
fun AppThemeItem(@StringRes modeResId: Int, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickableWithRipple { onClick() }
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(modeResId),
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular16
        )

        val radioButtonColors = RadioButtonColors(
            selectedColor = PomoroDoTheme.colorScheme.primaryContainer,
            unselectedColor = PomoroDoTheme.colorScheme.dialogGray50,
            disabledSelectedColor = Color.Unspecified,
            disabledUnselectedColor = Color.Unspecified
        )

        NoPaddingRadioButton(
            selected = selected,
            onClick = onClick,
            colors = radioButtonColors
        )
    }
}
package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.theme.palettesPrimary90

@Composable
fun CustomSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = { onCheckedChange(it) },
        modifier = Modifier
            .scale((18f / 32))
            .size(width = 30.dp, height = 19.dp),
        colors = SwitchDefaults.colors(
            checkedThumbColor = PomoroDoTheme.colorScheme.primaryContainer,
            checkedTrackColor = palettesPrimary90,
            uncheckedThumbColor = PomoroDoTheme.colorScheme.gray30,
            uncheckedTrackColor = PomoroDoTheme.colorScheme.gray70,
            uncheckedBorderColor = PomoroDoTheme.colorScheme.gray30,
        )
    )
}
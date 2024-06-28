package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.tico.pomorodo.R

@Composable
fun CustomTimeText(
    title: String,
    hour: Int,
    minute: Int,
    second: Int? = null,
    textColor: Color,
    spaceDp: Dp,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle = titleTextStyle,
    onClick: () -> Unit = {}
) {
    val text =
        if (second == null) stringResource(id = R.string.format_hour_minute, hour, minute)
        else stringResource(id = R.string.format_hour_minute_second, hour, minute, second)

    Column(
        modifier = Modifier.clickableWithoutRipple { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = titleTextStyle
        )

        Spacer(modifier = Modifier.height(spaceDp))

        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            style = contentTextStyle
        )
    }
}
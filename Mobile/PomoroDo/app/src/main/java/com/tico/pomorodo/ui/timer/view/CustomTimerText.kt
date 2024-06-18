package com.tico.pomorodo.ui.timer.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.tico.pomorodo.R
import java.util.Locale

@Composable
fun CustomTimeText(
    title: String,
    hour: Int,
    minute: Int,
    second: Int,
    textColor: Color,
    spacedBy: Dp,
    textStyle: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacedBy),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyle
        )

        Text(
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute_second),
                hour,
                minute,
                second
            ),
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyle
        )
    }
}

@Composable
fun CustomTimeText(
    title: String,
    hour: Int,
    minute: Int,
    second: Int,
    textColor: Color,
    spacedBy: Dp,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacedBy),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = titleTextStyle
        )

        Text(
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute_second),
                hour,
                minute,
                second
            ),
            color = textColor,
            textAlign = TextAlign.Center,
            style = contentTextStyle
        )
    }
}

@Composable
fun CustomTimeText(
    title: String,
    hour: Int,
    minute: Int,
    textColor: Color,
    spacedBy: Dp,
    vararg textStyles: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacedBy),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyles.first()
        )

        Text(
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute),
                hour,
                minute
            ),
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyles.last()
        )
    }
}

@Composable
fun CustomTimeText(
    title: String,
    hour: Int,
    minute: Int,
    textColor: Color,
    spacedBy: Dp,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacedBy),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = titleTextStyle
        )

        Text(
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute_second),
                hour,
                minute,
            ),
            color = textColor,
            textAlign = TextAlign.Center,
            style = contentTextStyle
        )
    }
}
package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    spaceDp: Dp,
    textStyle: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyle
        )

        Spacer(modifier = Modifier.height(spaceDp))

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
    spaceDp: Dp,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyle
        )

        Spacer(modifier = Modifier.height(spaceDp))

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
    spaceDp: Dp,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle,
) {
    Column(
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
    spaceDp: Dp,
    textStyles: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyles
        )

        Spacer(modifier = Modifier.height(spaceDp))

        Text(
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute),
                hour,
                minute
            ),
            color = textColor,
            textAlign = TextAlign.Center,
            style = textStyles
        )
    }
}

@Composable
fun CustomTimeText(
    title: String,
    hour: Int,
    minute: Int,
    textColor: Color,
    spaceDp: Dp,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle,
) {
    Column(
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
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute),
                hour,
                minute,
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
    spaceDp: Dp,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() },
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
            text = String.format(
                Locale.KOREA,
                stringResource(id = R.string.format_hour_minute),
                hour,
                minute,
            ),
            color = textColor,
            textAlign = TextAlign.Center,
            style = contentTextStyle
        )
    }
}
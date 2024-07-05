package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    borderColor: Color = Color.Unspecified,
    borderWidth: Dp = 1.dp,
    containerColor: Color,
    contentColor: Color,
    disabledContainerColor: Color = PomoroDoTheme.colorScheme.gray70,
    disabledContentColor: Color = PomoroDoTheme.colorScheme.background,
    textStyle: TextStyle,
    verticalPadding: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickableWithRipple(10.dp, enabled) { onClick() }
            .background(if (enabled) containerColor else disabledContainerColor)
            .border(borderWidth, borderColor, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
    ) {
        SimpleText(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = textStyle,
            color = if (enabled) contentColor else disabledContentColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    borderColor: Color = Color.Unspecified,
    containerColor: Color,
    contentColor: Color,
    disabledContainerColor: Color = PomoroDoTheme.colorScheme.gray70,
    disabledContentColor: Color = PomoroDoTheme.colorScheme.background,
    textStyle: TextStyle,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickableWithRipple(10.dp, enabled) { onClick() }
            .background(if (enabled) containerColor else disabledContainerColor)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .padding(vertical = verticalPadding, horizontal = horizontalPadding)
    ) {
        SimpleText(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = textStyle,
            color = if (enabled) contentColor else disabledContentColor,
            textAlign = TextAlign.Center
        )
    }
}
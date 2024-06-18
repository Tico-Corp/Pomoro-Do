package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun CustomTextButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    textStyle: TextStyle,
    verticalPadding: Dp,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp),
        enabled = true,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = PomoroDoTheme.colorScheme.gray70,
            disabledContentColor = PomoroDoTheme.colorScheme.background
        ),
        contentPadding = PaddingValues(vertical = verticalPadding)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Composable
fun CustomTextButton(
    text: String,
    enable: Boolean,
    containerColor: Color,
    contentColor: Color,
    disabledContainerColor: Color,
    disabledContentColor: Color,
    textStyle: TextStyle,
    verticalPadding: Dp,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp),
        enabled = enable,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = PaddingValues(vertical = verticalPadding)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Composable
fun CustomTextButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    textStyle: TextStyle,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp),
        enabled = true,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = PomoroDoTheme.colorScheme.gray70,
            disabledContentColor = PomoroDoTheme.colorScheme.background
        ),
        contentPadding = PaddingValues(vertical = verticalPadding, horizontal = horizontalPadding)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Composable
fun CustomTextButton(
    text: String,
    enable: Boolean,
    containerColor: Color,
    contentColor: Color,
    disabledContainerColor: Color,
    disabledContentColor: Color,
    textStyle: TextStyle,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp),
        enabled = enable,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = PaddingValues(vertical = verticalPadding, horizontal = horizontalPadding)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}
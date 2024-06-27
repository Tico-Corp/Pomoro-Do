package com.tico.pomorodo.ui.common.view

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun SimpleIcon(
    modifier: Modifier = Modifier,
    size: Int,
    imageVector: ImageVector,
    @StringRes contentDescriptionId: Int
) {
    Icon(
        modifier = modifier.size(size.dp),
        imageVector = imageVector,
        contentDescription = stringResource(id = contentDescriptionId),
        tint = Color.Unspecified
    )
}

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    size: Int,
    imageVector: ImageVector,
    @StringRes contentDescriptionId: Int,
    onClickedListener: () -> Unit,
    enabled: Boolean
) {
    IconButton(
        modifier = modifier
            .size(size.dp),
        onClick = onClickedListener,
        enabled = enabled
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(),
            imageVector = imageVector,
            contentDescription = stringResource(id = contentDescriptionId),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    style: TextStyle,
    color: Color,
    isEllipsis: Boolean = false,
    textAlign: TextAlign? = null
) {
    if (isEllipsis) {
        Text(
            modifier = modifier, text = stringResource(id = textId), style = style, color = color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = textAlign
        )
    } else {
        Text(
            modifier = modifier,
            text = stringResource(id = textId),
            style = style,
            color = color,
            textAlign = textAlign
        )
    }
}

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    color: Color,
    isEllipsis: Boolean = false,
    textAlign: TextAlign? = null
) {
    if (isEllipsis) {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            color = color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = textAlign
        )
    } else {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            color = color,
            textAlign = textAlign
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    callback: (() -> Unit)? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    singleLine: Boolean = true,
    colors: TextFieldColors,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = TextFieldDefaults.shape,
    contentPadding: PaddingValues = TextFieldDefaults.contentPaddingWithoutLabel(
        start = 2.dp, top = 0.dp, end = 2.dp, bottom = 3.dp
    )
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .indicatorLine(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = colors
            ),
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                callback?.invoke()
                keyboardController?.hide()
            },
        ),
        singleLine = singleLine
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            enabled = enabled,
            placeholder = placeholder,
            singleLine = singleLine,
            colors = colors,
            interactionSource = interactionSource,
            contentPadding = contentPadding,
            isError = isError,
            container = {
                TextFieldDefaults.Container(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    shape = shape,
                )
            },
            trailingIcon = trailingIcon
        )
    }
}

@Composable
fun SimpleWideTextButton(
    modifier: Modifier = Modifier,
    containColor: Color = Color.Unspecified,
    @StringRes textId: Int,
    textStyle: TextStyle,
    textColor: Color,
    borderColor: Color = Color.Unspecified,
    onClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable { onClicked() }
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .background(
                containColor,
                RoundedCornerShape(10.dp)
            )
            .padding(vertical = 12.dp)
    ) {
        SimpleText(
            modifier = Modifier.fillMaxWidth(),
            textId = textId,
            textAlign = TextAlign.Center,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun SimpleNarrowTextButton(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    textStyle: TextStyle,
    textColor: Color,
    containColor: Color = Color.Unspecified,
    disabledContainerColor: Color = Color.Unspecified,
    enabled: Boolean = true,
    borderColor: Color = Color.Unspecified,
    onClicked: () -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = modifier
            .clickable { if (enabled) onClicked() }
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .background(
                if (enabled) containColor else disabledContainerColor,
                RoundedCornerShape(5.dp)
            )
            .padding(paddingValues)
    ) {
        SimpleText(
            modifier = Modifier,
            textId = textId,
            textAlign = TextAlign.Center,
            style = textStyle,
            color = textColor
        )
    }
}
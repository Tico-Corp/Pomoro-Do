package com.tico.pomorodo.ui.common.view

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun FigureShape(
    modifier: Modifier = Modifier,
    shape: Shape,
    width: Int,
    height: Int,
    color: Color
) {
    Box(
        modifier = modifier
            .size(width = width.dp, height = height.dp)
            .clip(shape)
            .background(color)
    )
}

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
    isEllipsis: Boolean = false
) {
    if (isEllipsis) {
        Text(
            modifier = modifier, text = stringResource(id = textId), style = style, color = color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    } else {
        Text(modifier = modifier, text = stringResource(id = textId), style = style, color = color)
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
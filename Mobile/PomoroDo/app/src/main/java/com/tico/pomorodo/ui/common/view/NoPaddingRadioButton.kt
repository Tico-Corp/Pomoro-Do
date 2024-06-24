package com.tico.pomorodo.ui.common.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

private const val RadioAnimationDuration = 100

private val RadioButtonPadding = 2.dp
private val RadioButtonDotSize = 12.dp
private val RadioStrokeWidth = 2.dp
val StateLayerSize = 40.0.dp
val IconSize = 20.0.dp

@Composable
fun NoPaddingRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
    padding: PaddingValues = PaddingValues(RadioButtonPadding)
) {
    val dotRadius = animateDpAsState(
        targetValue = if (selected) RadioButtonDotSize / 2 else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration), label = ""
    )
    val radioColor = colors.radioColor(enabled, selected)
    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = StateLayerSize / 2
                )
            )
        } else {
            Modifier
        }
    Canvas(
        modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(padding)
            .requiredSize(IconSize)
    ) {
        val strokeWidth = RadioStrokeWidth.toPx()
        drawCircle(
            radioColor.value,
            radius = (IconSize / 2).toPx() - strokeWidth / 2,
            style = Stroke(strokeWidth)
        )
        if (dotRadius.value > 0.dp) {
            drawCircle(radioColor.value, dotRadius.value.toPx() - strokeWidth / 2, style = Fill)
        }
    }
}

@Composable
internal fun RadioButtonColors.radioColor(enabled: Boolean, selected: Boolean): State<Color> {
    val target = when {
        enabled && selected -> selectedColor
        enabled && !selected -> unselectedColor
        !enabled && selected -> disabledSelectedColor
        else -> disabledUnselectedColor
    }

    return if (enabled) {
        animateColorAsState(target, tween(durationMillis = RadioAnimationDuration), label = "")
    } else {
        rememberUpdatedState(target)
    }
}
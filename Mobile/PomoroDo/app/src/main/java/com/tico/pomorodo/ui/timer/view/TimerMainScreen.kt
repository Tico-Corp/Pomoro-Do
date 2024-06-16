package com.tico.pomorodo.ui.timer.view

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcTimerPin
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TimerMainScreen() {
    CustomCircularDraggableTimer(
        modifier = Modifier
            .size(300.dp)
            .background(PomoroDoTheme.colorScheme.background),
        timerColor = PomoroDoTheme.colorScheme.primaryContainer,
        backgroundColor = PomoroDoTheme.colorScheme.background,
        indicatorColor = PomoroDoTheme.colorScheme.onBackground,
        circleRadius = 125.dp,
        initialValue = 19,
    ) { position ->
        // do something with this position value
    }
}

@Composable
fun CustomCircularDraggableTimer(
    modifier: Modifier = Modifier,
    timerColor: Color,
    backgroundColor: Color,
    indicatorColor: Color,
    circleRadius: Dp,
    minValue: Int = 0,
    maxValue: Int = 120,
    initialValue: Int,
    onPositionChange: (Int) -> Unit,
) {
    val context = LocalContext.current
    val laundryGothicTypeface = remember {
        Typeface.create(
            ResourcesCompat.getFont(context, R.font.laundry_gothic_regular),
            Typeface.NORMAL
        )
    }

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableIntStateOf(initialValue)
    }

    var changeAngle by remember {
        mutableFloatStateOf(0f)
    }

    var dragStartAngle by remember {
        mutableFloatStateOf(0f)
    }

    var oldPositionValue by remember {
        mutableIntStateOf(initialValue)
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartAngle = -atan2(
                            x = circleCenter.y - offset.y,
                            y = circleCenter.x - offset.x
                        ) * (180f / PI).toFloat()

                        dragStartAngle = dragStartAngle.mod(360f)
                    },
                    onDrag = { change, _ ->
                        var touchAngle = -atan2(
                            x = circleCenter.y - change.position.y,
                            y = circleCenter.x - change.position.x
                        ) * (180f / PI).toFloat()

                        touchAngle = touchAngle.mod(360f)

                        val currentAngle = oldPositionValue * 360f / (maxValue - minValue)
                        changeAngle = touchAngle - currentAngle

                        val lowerThreshold = currentAngle - (360f / (maxValue - minValue) * 5)
                        val higherThreshold = currentAngle + (360f / (maxValue - minValue) * 5)

                        if (dragStartAngle in lowerThreshold..higherThreshold) {
                            positionValue =
                                (oldPositionValue + (changeAngle / (360f / (maxValue - minValue))).roundToInt())
                        }
                    },
                    onDragEnd = {
                        oldPositionValue = positionValue
                        onPositionChange(positionValue)
                    }
                )
            }
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            val circleRadiusToPx = circleRadius.toPx()
            circleCenter = Offset(x = width / 2f, y = height / 2f)

            drawCircle(
                color = backgroundColor,
                radius = circleRadiusToPx,
                center = circleCenter,
            )

            drawArc(
                color = timerColor,
                startAngle = -90f,
                sweepAngle = (360f / maxValue) * positionValue.toFloat(),
                style = Fill,
                useCenter = true,
                size = Size(width = circleRadiusToPx * 2f, height = circleRadiusToPx * 2f),
                topLeft = Offset(
                    x = (width - circleRadiusToPx * 2f) / 2f,
                    y = (height - circleRadiusToPx * 2f) / 2f
                )
            )

            val outerRadius = circleRadiusToPx + circleThickness / 2f
            val gap = 15f

            val shortIndicatorLength = 10f
            val longIndicatorLength = 15f
            for (i in 0..(maxValue - minValue)) {
                val isLongIndicator = i % 10 == 0
                val length = if (isLongIndicator) longIndicatorLength else shortIndicatorLength
                val color =
                    if (i % 5 != 0) Color.Unspecified else indicatorColor
                val angleInDegrees = i * 360f / (maxValue - minValue).toFloat()
                val angleInRadius = angleInDegrees * PI / 180f + PI / 2f

                val yGapAdjustment = cos(angleInDegrees * PI / 180f) * gap
                val xGapAdjustment = -sin(angleInDegrees * PI / 180f) * gap

                val start = Offset(
                    x = ((outerRadius + length) * cos(angleInRadius) + circleCenter.x - xGapAdjustment).toFloat(),
                    y = ((outerRadius + length) * sin(angleInRadius) + circleCenter.y - yGapAdjustment).toFloat()
                )
                val end = Offset(
                    x = ((outerRadius - length) * cos(angleInRadius) + circleCenter.x - xGapAdjustment).toFloat(),
                    y = ((outerRadius - length) * sin(angleInRadius) + circleCenter.y - yGapAdjustment).toFloat()
                )

                drawLine(color = color, start = start, end = end, strokeWidth = 1.3.dp.toPx())
            }

            // Adding the numbers outside the circle
            for (i in 0 until (maxValue - minValue) step 10) {
                val angleInDegrees = i * 360f / (maxValue - minValue).toFloat()
                val angleInRadius = angleInDegrees * PI / 180f - PI / 2f

                val textRadius = outerRadius + longIndicatorLength + 20f
                val x = (textRadius * cos(angleInRadius) + circleCenter.x).toFloat()
                val y = (textRadius * sin(angleInRadius) + circleCenter.y + 10).toFloat()

                val text = i.toString()

                drawContext.canvas.nativeCanvas.apply {
                    drawIntoCanvas {
                        drawText(
                            text,
                            x,
                            y,
                            Paint().apply {
                                textSize = 12.sp.toPx()
                                textAlign = Paint.Align.CENTER
                                color = indicatorColor.toArgb()
                                typeface = laundryGothicTypeface
                            }
                        )
                    }
                }
            }
        }

        Icon(
            imageVector = IconPack.IcTimerPin,
            contentDescription = stringResource(R.string.content_ic_timer_pin),
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PomoroDoTheme {
        CustomCircularDraggableTimer(
            modifier = Modifier
                .size(300.dp)
                .background(PomoroDoTheme.colorScheme.background),
            timerColor = PomoroDoTheme.colorScheme.primaryContainer,
            backgroundColor = PomoroDoTheme.colorScheme.background,
            indicatorColor = PomoroDoTheme.colorScheme.onBackground,
            circleRadius = 125.dp,
            initialValue = 19,
        ) { position ->
            // do something with this position value
        }
    }
}
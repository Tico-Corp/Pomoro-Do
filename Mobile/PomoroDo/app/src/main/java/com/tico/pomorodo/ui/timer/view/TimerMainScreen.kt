package com.tico.pomorodo.ui.timer.view

import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTimeText
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcTimerPin
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TimerRootScreen() {
    var concentrationTime by remember {
        mutableIntStateOf(30)
    }
    var breakTime by remember {
        mutableIntStateOf(70)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PomodoroTimerScreen(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            onConcentrationTimeChange = { position -> concentrationTime = position },
            onBreakTimeChange = { position -> breakTime = position }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextButton(
            text = "집중 시작하기",
            backgroundColor = PomoroDoTheme.colorScheme.primaryContainer,
            textColor = PomoroDoTheme.colorScheme.background,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp
        ) { /*TODO*/ }
    }
}

@Composable
fun PomodoroTimerScreen(
    concentrationTime: Int,
    breakTime: Int,
    onConcentrationTimeChange: (Int) -> Unit,
    onBreakTimeChange: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomPomodoroTimer(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            onConcentrationTimeChange = onConcentrationTimeChange,
            onBreakTimeChange = onBreakTimeChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        TodayConcentrationInformation()
    }
}

@Composable
fun TodayConcentrationInformation() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTimeText(
            title = stringResource(R.string.title_taget_concentration),
            hour = 12,
            minute = 0,
            second = 0,
            textColor = PomoroDoTheme.colorScheme.onBackground,
            spaceDp = 4.dp,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTimeText(
                title = stringResource(R.string.title_total_concentration),
                hour = 10,
                minute = 0,
                second = 0,
                textColor = PomoroDoTheme.colorScheme.onBackground,
                spaceDp = 4.dp,
                textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            )

            Spacer(modifier = Modifier.width(50.dp))

            CustomTimeText(
                title = stringResource(R.string.title_total_break),
                hour = 3,
                minute = 0,
                second = 0,
                textColor = PomoroDoTheme.colorScheme.onBackground,
                spaceDp = 4.dp,
                textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            )
        }
    }
}

@Composable
fun CustomPomodoroTimer(
    concentrationTime: Int,
    breakTime: Int,
    onConcentrationTimeChange: (Int) -> Unit,
    onBreakTimeChange: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditableTextTimer(
            concentrationTime = concentrationTime,
            breakTime = breakTime,
            concentrationColor = PomoroDoTheme.colorScheme.primaryContainer,
            breakColor = PomoroDoTheme.colorScheme.secondaryContainer
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomCircularDraggableTimer(
            modifier = Modifier
                .size(300.dp)
                .background(PomoroDoTheme.colorScheme.background),
            outerTimerColor = PomoroDoTheme.colorScheme.primaryContainer,
            innerTimerColor = PomoroDoTheme.colorScheme.secondaryContainer,
            backgroundColor = PomoroDoTheme.colorScheme.background,
            indicatorColor = PomoroDoTheme.colorScheme.onBackground,
            outerCircleRadius = 125,
            outerInitialValue = concentrationTime,
            innerInitialValue = breakTime,
            onOuterPositionChange = { position -> onConcentrationTimeChange(position) },
            onInnerPositionChange = { position -> onBreakTimeChange(position) }
        )
    }
}

@Composable
fun EditableTextTimer(
    concentrationTime: Int,
    breakTime: Int,
    concentrationColor: Color,
    breakColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomTimeText(
            title = stringResource(R.string.title_concentration_time),
            hour = concentrationTime / 60,
            minute = concentrationTime % 60,
            textColor = concentrationColor,
            spaceDp = 10.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular16
        )

        Spacer(modifier = Modifier.width(20.dp))

        CustomTimeText(
            title = stringResource(R.string.title_break_time),
            hour = breakTime / 60,
            minute = breakTime % 60,
            textColor = breakColor,
            spaceDp = 10.dp,
            titleTextStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            contentTextStyle = PomoroDoTheme.typography.laundryGothicRegular16
        )
    }
}

@Composable
fun CustomCircularDraggableTimer(
    modifier: Modifier = Modifier,
    outerTimerColor: Color,
    innerTimerColor: Color,
    backgroundColor: Color,
    indicatorColor: Color,
    outerCircleRadius: Int,
    minValue: Int = 0,
    maxValue: Int = 120,
    outerInitialValue: Int,
    innerInitialValue: Int,
    onOuterPositionChange: (Int) -> Unit,
    onInnerPositionChange: (Int) -> Unit,
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

    var oldPositionValue by remember {
        mutableIntStateOf(0)
    }

    var outerPositionValue by remember {
        mutableIntStateOf(outerInitialValue)
    }

    var outerOldPositionValue by remember {
        mutableIntStateOf(outerInitialValue)
    }

    var innerPositionValue by remember {
        mutableIntStateOf(innerInitialValue)
    }

    var innerOldPositionValue by remember {
        mutableIntStateOf(innerInitialValue)
    }

    var changeAngle by remember {
        mutableFloatStateOf(0f)
    }

    var dragStartAngle by remember {
        mutableFloatStateOf(0f)
    }

    var distanceBetweenDragStartAndCircleCenter by remember {
        mutableFloatStateOf(0f)
    }

    var isOuterCircle by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { offset ->
                        distanceBetweenDragStartAndCircleCenter =
                            hypot(x = offset.x - circleCenter.x, y = offset.y - circleCenter.y)

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


                        isOuterCircle =
                            distanceBetweenDragStartAndCircleCenter > (outerCircleRadius / 2).dp.toPx()
                        oldPositionValue =
                            if (isOuterCircle) outerOldPositionValue else innerOldPositionValue

                        val currentAngle = oldPositionValue * 360f / (maxValue - minValue)
                        changeAngle = touchAngle - currentAngle

                        val lowerThreshold = currentAngle - (360f / (maxValue - minValue) * 5)
                        val higherThreshold = currentAngle + (360f / (maxValue - minValue) * 5)

                        if (dragStartAngle in lowerThreshold..higherThreshold) {
                            if (isOuterCircle) outerPositionValue =
                                (oldPositionValue + (changeAngle / (360f / (maxValue - minValue))).roundToInt())
                            else innerPositionValue =
                                (oldPositionValue + (changeAngle / (360f / (maxValue - minValue))).roundToInt())
                        }

                        if (isOuterCircle) {
                            onOuterPositionChange(outerPositionValue)
                        } else {
                            onInnerPositionChange(innerPositionValue)
                        }

                        Log.d(
                            "TimerTest",
                            "currentAngle: $currentAngle, dragStartAngle: $dragStartAngle, distance: $distanceBetweenDragStartAndCircleCenter"
                        )
                    },
                    onDragEnd = {
                        if (isOuterCircle) {
                            outerOldPositionValue = outerPositionValue
                            onOuterPositionChange(outerPositionValue)
                        } else {
                            innerOldPositionValue = innerPositionValue
                            onInnerPositionChange(innerPositionValue)
                        }
                    }
                )
            }
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            val outerCircleRadiusToPx = outerCircleRadius.dp.toPx()
            val innerCircleRadiusToPx = outerCircleRadiusToPx / 2
            circleCenter = Offset(x = width / 2f, y = height / 2f)

            drawCircle(
                color = backgroundColor,
                radius = outerCircleRadiusToPx,
                center = circleCenter,
            )

            drawArc(
                color = outerTimerColor,
                startAngle = -90f,
                sweepAngle = (360f / maxValue) * outerPositionValue.toFloat(),
                style = Fill,
                useCenter = true,
                size = Size(
                    width = outerCircleRadiusToPx * 2f,
                    height = outerCircleRadiusToPx * 2f
                ),
                topLeft = Offset(
                    x = (width - outerCircleRadiusToPx * 2f) / 2f,
                    y = (height - outerCircleRadiusToPx * 2f) / 2f
                )
            )

            drawArc(
                color = innerTimerColor,
                startAngle = -90f,
                sweepAngle = (360f / maxValue) * innerPositionValue.toFloat(),
                style = Fill,
                useCenter = true,
                size = Size(
                    width = innerCircleRadiusToPx * 2f,
                    height = innerCircleRadiusToPx * 2f
                ),
                topLeft = Offset(
                    x = (width - innerCircleRadiusToPx * 2f) / 2f,
                    y = (height - innerCircleRadiusToPx * 2f) / 2f
                )
            )

            val outerRadius = outerCircleRadiusToPx + circleThickness / 2f
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
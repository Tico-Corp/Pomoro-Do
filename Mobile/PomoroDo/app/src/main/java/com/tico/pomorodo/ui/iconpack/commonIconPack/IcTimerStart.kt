package com.tico.pomorodo.ui.iconpack.commonIconPack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcTimerPin: ImageVector
    get() {
        if (_icTimerStart != null) {
            return _icTimerStart!!
        }
        _icTimerStart = Builder(
            name = "IcTimerStart", defaultWidth = 32.0.dp, defaultHeight =
            31.0.dp, viewportWidth = 32.0f, viewportHeight = 31.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF00B21D)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.2185f, 2.9669f)
                curveTo(13.458f, -0.6279f, 18.542f, -0.6279f, 19.7815f, 2.9669f)
                lineTo(20.9077f, 6.233f)
                curveTo(21.464f, 7.8464f, 22.9827f, 8.9291f, 24.6892f, 8.9291f)
                horizontalLineTo(27.8042f)
                curveTo(31.7364f, 8.9291f, 33.3085f, 14.0083f, 30.0639f, 16.2297f)
                lineTo(27.9607f, 17.6695f)
                curveTo(26.4748f, 18.6868f, 25.8519f, 20.5716f, 26.4389f, 22.274f)
                lineTo(27.3373f, 24.8795f)
                curveTo(28.5937f, 28.5233f, 24.4765f, 31.6614f, 21.2961f, 29.484f)
                lineTo(18.2597f, 27.4052f)
                curveTo(16.8975f, 26.4727f, 15.1025f, 26.4727f, 13.7403f, 27.4052f)
                lineTo(10.7039f, 29.484f)
                curveTo(7.5235f, 31.6614f, 3.4063f, 28.5233f, 4.6627f, 24.8795f)
                lineTo(5.5611f, 22.274f)
                curveTo(6.1481f, 20.5716f, 5.5252f, 18.6868f, 4.0393f, 17.6695f)
                lineTo(1.9361f, 16.2297f)
                curveTo(-1.3085f, 14.0083f, 0.2636f, 8.9291f, 4.1958f, 8.9291f)
                horizontalLineTo(7.3108f)
                curveTo(9.0173f, 8.9291f, 10.536f, 7.8464f, 11.0923f, 6.233f)
                lineTo(12.2185f, 2.9669f)
                close()
            }
        }
            .build()
        return _icTimerStart!!
    }

private var _icTimerStart: ImageVector? = null

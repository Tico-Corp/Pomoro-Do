package com.tico.pomorodo.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcTodoGoingLight: ImageVector
    get() {
        if (_icTodoGoingLight != null) {
            return _icTodoGoingLight!!
        }
        _icTodoGoingLight = Builder(
            name = "IcTodoGoingLight", defaultWidth = 96.0.dp, defaultHeight
            = 96.0.dp, viewportWidth = 96.0f, viewportHeight = 96.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd
                ) {
                    moveTo(20.9216f, 12.0f)
                    curveTo(15.9944f, 12.0f, 12.0f, 15.9944f, 12.0f, 20.9216f)
                    verticalLineTo(75.0784f)
                    curveTo(12.0f, 80.0056f, 15.9944f, 84.0f, 20.9216f, 84.0f)
                    horizontalLineTo(75.0784f)
                    curveTo(80.0056f, 84.0f, 84.0f, 80.0056f, 84.0f, 75.0784f)
                    verticalLineTo(20.9216f)
                    curveTo(84.0f, 15.9944f, 80.0056f, 12.0f, 75.0784f, 12.0f)
                    horizontalLineTo(20.9216f)
                    close()
                    moveTo(45.5581f, 25.4106f)
                    curveTo(46.7329f, 23.5291f, 49.4727f, 23.5291f, 50.6475f, 25.4106f)
                    lineTo(75.745f, 65.6064f)
                    curveTo(76.9926f, 67.6046f, 75.556f, 70.1953f, 73.2003f, 70.1953f)
                    horizontalLineTo(23.0053f)
                    curveTo(20.6496f, 70.1953f, 19.2129f, 67.6046f, 20.4606f, 65.6064f)
                    lineTo(45.5581f, 25.4106f)
                    close()
                    moveTo(45.7331f, 42.507f)
                    curveTo(46.9015f, 40.5843f, 49.6921f, 40.5843f, 50.8605f, 42.507f)
                    lineTo(59.909f, 57.3969f)
                    curveTo(61.1239f, 59.3961f, 59.6847f, 61.9549f, 57.3453f, 61.9549f)
                    horizontalLineTo(39.2483f)
                    curveTo(36.9089f, 61.9549f, 35.4697f, 59.3961f, 36.6846f, 57.3969f)
                    lineTo(45.7331f, 42.507f)
                    close()
                }
            }
        }
            .build()
        return _icTodoGoingLight!!
    }

private var _icTodoGoingLight: ImageVector? = null

package com.tico.pomorodo.ui.iconpack.darkiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcSearchDark: ImageVector
    get() {
        if (_icSearchDark != null) {
            return _icSearchDark!!
        }
        _icSearchDark = Builder(
            name = "IcSearchDark", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFB4B4B4)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(15.5f, 14.0f)
                    horizontalLineTo(14.71f)
                    lineTo(14.43f, 13.73f)
                    curveTo(15.41f, 12.59f, 16.0f, 11.11f, 16.0f, 9.5f)
                    curveTo(16.0f, 5.91f, 13.09f, 3.0f, 9.5f, 3.0f)
                    curveTo(5.91f, 3.0f, 3.0f, 5.91f, 3.0f, 9.5f)
                    curveTo(3.0f, 13.09f, 5.91f, 16.0f, 9.5f, 16.0f)
                    curveTo(11.11f, 16.0f, 12.59f, 15.41f, 13.73f, 14.43f)
                    lineTo(14.0f, 14.71f)
                    verticalLineTo(15.5f)
                    lineTo(19.0f, 20.49f)
                    lineTo(20.49f, 19.0f)
                    lineTo(15.5f, 14.0f)
                    close()
                    moveTo(9.5f, 14.0f)
                    curveTo(7.01f, 14.0f, 5.0f, 11.99f, 5.0f, 9.5f)
                    curveTo(5.0f, 7.01f, 7.01f, 5.0f, 9.5f, 5.0f)
                    curveTo(11.99f, 5.0f, 14.0f, 7.01f, 14.0f, 9.5f)
                    curveTo(14.0f, 11.99f, 11.99f, 14.0f, 9.5f, 14.0f)
                    close()
                }
            }
        }
            .build()
        return _icSearchDark!!
    }

private var _icSearchDark: ImageVector? = null

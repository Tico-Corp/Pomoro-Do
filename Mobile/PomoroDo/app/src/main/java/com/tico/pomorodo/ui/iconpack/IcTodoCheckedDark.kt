package com.tico.pomorodo.ui.iconpack

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
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcTodoCheckedDark: ImageVector
    get() {
        if (_icTodoCheckedDark != null) {
            return _icTodoCheckedDark!!
        }
        _icTodoCheckedDark = Builder(
            name = "IcTodoCheckedDark", defaultWidth = 96.0.dp,
            defaultHeight = 96.0.dp, viewportWidth = 96.0f, viewportHeight = 96.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFF5F5F5)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(76.0f, 12.0f)
                    horizontalLineTo(20.0f)
                    curveTo(15.56f, 12.0f, 12.0f, 15.6f, 12.0f, 20.0f)
                    verticalLineTo(76.0f)
                    curveTo(12.0f, 80.4f, 15.56f, 84.0f, 20.0f, 84.0f)
                    horizontalLineTo(76.0f)
                    curveTo(80.44f, 84.0f, 84.0f, 80.4f, 84.0f, 76.0f)
                    verticalLineTo(20.0f)
                    curveTo(84.0f, 15.6f, 80.44f, 12.0f, 76.0f, 12.0f)
                    close()
                    moveTo(40.0f, 68.0f)
                    lineTo(20.0f, 48.0f)
                    lineTo(25.64f, 42.36f)
                    lineTo(40.0f, 56.68f)
                    lineTo(70.36f, 26.32f)
                    lineTo(76.0f, 32.0f)
                    lineTo(40.0f, 68.0f)
                    close()
                }
            }
        }
            .build()
        return _icTodoCheckedDark!!
    }

private var _icTodoCheckedDark: ImageVector? = null

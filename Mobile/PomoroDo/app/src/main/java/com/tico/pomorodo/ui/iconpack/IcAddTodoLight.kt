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

val IconPack.IcAddTodoLight: ImageVector
    get() {
        if (_icAddTodoLight != null) {
            return _icAddTodoLight!!
        }
        _icAddTodoLight = Builder(
            name = "IcAddTodoLight", defaultWidth = 20.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF875224)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(4.4453f, 5.5557f)
                    horizontalLineToRelative(11.1111f)
                    verticalLineToRelative(10.0f)
                    horizontalLineToRelative(-11.1111f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFFDFDFD)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(9.9993f, 18.3333f)
                    curveTo(5.3994f, 18.3333f, 1.666f, 14.5999f, 1.666f, 9.9999f)
                    curveTo(1.666f, 5.3999f, 5.3994f, 1.6666f, 9.9993f, 1.6666f)
                    curveTo(14.5993f, 1.6666f, 18.3327f, 5.3999f, 18.3327f, 9.9999f)
                    curveTo(18.3327f, 14.5999f, 14.5993f, 18.3333f, 9.9993f, 18.3333f)
                    close()
                    moveTo(14.166f, 9.1666f)
                    horizontalLineTo(10.8327f)
                    verticalLineTo(5.8333f)
                    horizontalLineTo(9.166f)
                    verticalLineTo(9.1666f)
                    horizontalLineTo(5.8327f)
                    verticalLineTo(10.8333f)
                    horizontalLineTo(9.166f)
                    verticalLineTo(14.1666f)
                    horizontalLineTo(10.8327f)
                    verticalLineTo(10.8333f)
                    horizontalLineTo(14.166f)
                    verticalLineTo(9.1666f)
                    close()
                }
            }
        }
            .build()
        return _icAddTodoLight!!
    }

private var _icAddTodoLight: ImageVector? = null

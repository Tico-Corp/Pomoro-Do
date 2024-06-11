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
import com.tico.pomorodo.ui.theme.DarkIconPack

val DarkIconPack.IcAddTodoDark: ImageVector
    get() {
        if (_icAddTodoDark != null) {
            return _icAddTodoDark!!
        }
        _icAddTodoDark = Builder(
            name = "IcAddTodoDark", defaultWidth = 20.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFEDEDED)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(9.9993f, 18.3333f)
                    curveTo(5.3994f, 18.3333f, 1.666f, 14.6f, 1.666f, 10.0f)
                    curveTo(1.666f, 5.4f, 5.3994f, 1.6667f, 9.9993f, 1.6667f)
                    curveTo(14.5993f, 1.6667f, 18.3327f, 5.4f, 18.3327f, 10.0f)
                    curveTo(18.3327f, 14.6f, 14.5993f, 18.3333f, 9.9993f, 18.3333f)
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
        return _icAddTodoDark!!
    }

private var _icAddTodoDark: ImageVector? = null

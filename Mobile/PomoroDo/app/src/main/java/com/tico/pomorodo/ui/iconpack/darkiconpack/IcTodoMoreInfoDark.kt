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

val DarkIconPack.IcTodoMoreInfoDark: ImageVector
    get() {
        if (_icTodoMoreInfoDark != null) {
            return _icTodoMoreInfoDark!!
        }
        _icTodoMoreInfoDark = Builder(
            name = "IcTodoMoreInfoDark", defaultWidth = 15.0.dp,
            defaultHeight = 15.0.dp, viewportWidth = 15.0f, viewportHeight = 15.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF898989)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(3.75f, 6.25f)
                    curveTo(3.0625f, 6.25f, 2.5f, 6.8125f, 2.5f, 7.5f)
                    curveTo(2.5f, 8.1875f, 3.0625f, 8.75f, 3.75f, 8.75f)
                    curveTo(4.4375f, 8.75f, 5.0f, 8.1875f, 5.0f, 7.5f)
                    curveTo(5.0f, 6.8125f, 4.4375f, 6.25f, 3.75f, 6.25f)
                    close()
                    moveTo(11.25f, 6.25f)
                    curveTo(10.5625f, 6.25f, 10.0f, 6.8125f, 10.0f, 7.5f)
                    curveTo(10.0f, 8.1875f, 10.5625f, 8.75f, 11.25f, 8.75f)
                    curveTo(11.9375f, 8.75f, 12.5f, 8.1875f, 12.5f, 7.5f)
                    curveTo(12.5f, 6.8125f, 11.9375f, 6.25f, 11.25f, 6.25f)
                    close()
                    moveTo(7.5f, 6.25f)
                    curveTo(6.8125f, 6.25f, 6.25f, 6.8125f, 6.25f, 7.5f)
                    curveTo(6.25f, 8.1875f, 6.8125f, 8.75f, 7.5f, 8.75f)
                    curveTo(8.1875f, 8.75f, 8.75f, 8.1875f, 8.75f, 7.5f)
                    curveTo(8.75f, 6.8125f, 8.1875f, 6.25f, 7.5f, 6.25f)
                    close()
                }
            }
        }
            .build()
        return _icTodoMoreInfoDark!!
    }

private var _icTodoMoreInfoDark: ImageVector? = null

package com.tico.pomorodo.ui.iconpack.commonIconPack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcGroupSelectedChecked: ImageVector
    get() {
        if (_icGroupSelectedChecked != null) {
            return _icGroupSelectedChecked!!
        }
        _icGroupSelectedChecked = Builder(
            name = "IcGroupSelectedChecked", defaultWidth = 20.0.dp,
            defaultHeight = 20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFF9A40)), stroke = null, fillAlpha = 0.15f, strokeAlpha
                = 0.15f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(17.5f, 10.0f)
                curveTo(17.5f, 14.1422f, 14.1422f, 17.5f, 10.0f, 17.5f)
                curveTo(5.8579f, 17.5f, 2.5f, 14.1422f, 2.5f, 10.0f)
                curveTo(2.5f, 5.8579f, 5.8579f, 2.5f, 10.0f, 2.5f)
                curveTo(14.1422f, 2.5f, 17.5f, 5.8579f, 17.5f, 10.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFF9A40)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(14.1667f, 7.5f)
                lineTo(8.3333f, 13.3333f)
                lineTo(5.8333f, 10.8333f)
                moveTo(17.5f, 10.0f)
                curveTo(17.5f, 14.1422f, 14.1422f, 17.5f, 10.0f, 17.5f)
                curveTo(5.8579f, 17.5f, 2.5f, 14.1422f, 2.5f, 10.0f)
                curveTo(2.5f, 5.8579f, 5.8579f, 2.5f, 10.0f, 2.5f)
                curveTo(14.1422f, 2.5f, 17.5f, 5.8579f, 17.5f, 10.0f)
                close()
            }
        }
            .build()
        return _icGroupSelectedChecked!!
    }

private var _icGroupSelectedChecked: ImageVector? = null

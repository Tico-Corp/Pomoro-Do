package com.tico.pomorodo.ui.iconpack.lighticonpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.LightIconPack

val LightIconPack.IcUnokLight: ImageVector
    get() {
        if (_icUnokLight != null) {
            return _icUnokLight!!
        }
        _icUnokLight = Builder(
            name = "IcUnokLight", defaultWidth = 28.0.dp, defaultHeight =
            28.0.dp, viewportWidth = 28.0f, viewportHeight = 28.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFD9D9D9)),
                strokeLineWidth = 2.08696f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(21.0f, 9.0f)
                lineTo(12.0f, 19.0f)
                lineTo(7.0f, 15.0f)
            }
        }
            .build()
        return _icUnokLight!!
    }

private var _icUnokLight: ImageVector? = null

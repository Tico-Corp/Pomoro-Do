package com.tico.pomorodo.ui.iconpack.lighticonpack

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
import com.tico.pomorodo.ui.theme.LightIconPack

val LightIconPack.IcCalendarDropDownLight: ImageVector
    get() {
        if (_icCalendarDropDownLight != null) {
            return _icCalendarDropDownLight!!
        }
        _icCalendarDropDownLight = Builder(
            name = "IcCalendarDropDownLight", defaultWidth = 19.0.dp,
            defaultHeight = 19.0.dp, viewportWidth = 19.0f, viewportHeight = 19.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(5.8663f, 6.8005f)
                    lineTo(9.5f, 10.4264f)
                    lineTo(13.1338f, 6.8005f)
                    lineTo(14.25f, 7.9168f)
                    lineTo(9.5f, 12.6668f)
                    lineTo(4.75f, 7.9168f)
                    lineTo(5.8663f, 6.8005f)
                    close()
                }
            }
        }
            .build()
        return _icCalendarDropDownLight!!
    }

private var _icCalendarDropDownLight: ImageVector? = null

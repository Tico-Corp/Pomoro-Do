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

val DarkIconPack.IcCalendarDropDownDark: ImageVector
    get() {
        if (_icCalendarDropDownDark != null) {
            return _icCalendarDropDownDark!!
        }
        _icCalendarDropDownDark = Builder(
            name = "IcCalendarDropDownDark", defaultWidth = 19.0.dp,
            defaultHeight = 19.0.dp, viewportWidth = 19.0f, viewportHeight = 19.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFF5F5F5)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(5.8663f, 6.8004f)
                    lineTo(9.5f, 10.4262f)
                    lineTo(13.1337f, 6.8004f)
                    lineTo(14.25f, 7.9167f)
                    lineTo(9.5f, 12.6667f)
                    lineTo(4.75f, 7.9167f)
                    lineTo(5.8663f, 6.8004f)
                    close()
                }
            }
        }
            .build()
        return _icCalendarDropDownDark!!
    }

private var _icCalendarDropDownDark: ImageVector? = null

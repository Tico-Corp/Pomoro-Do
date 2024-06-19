package com.tico.pomorodo.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcCalendarChecked: ImageVector
    get() {
        if (_icCalendarChecked != null) {
            return _icCalendarChecked!!
        }
        _icCalendarChecked = Builder(
            name = "IcCalendarChecked", defaultWidth = 17.0.dp,
            defaultHeight = 17.0.dp, viewportWidth = 17.0f, viewportHeight = 17.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF241912)),
                    strokeLineWidth = 1.61455f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(13.9149f, 4.509f)
                    lineTo(6.9522f, 12.2454f)
                    lineTo(3.084f, 9.1509f)
                }
            }
        }
            .build()
        return _icCalendarChecked!!
    }

private var _icCalendarChecked: ImageVector? = null

package com.tico.pomorodo.ui.iconpack.darkiconpack

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
import com.tico.pomorodo.ui.theme.DarkIconPack

val DarkIconPack.IcGroupSelectedUncheckedDark: ImageVector
    get() {
        if (_icGroupSelectedUncheckedDark != null) {
            return _icGroupSelectedUncheckedDark!!
        }
        _icGroupSelectedUncheckedDark = Builder(
            name = "IcGroupSelectedUncheckedDark", defaultWidth
            = 20.0.dp, defaultHeight = 20.0.dp, viewportWidth = 20.0f, viewportHeight =
            20.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFD9D9D9)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(17.5f, 10.0f)
                    curveTo(17.5f, 14.1422f, 14.1422f, 17.5f, 10.0f, 17.5f)
                    curveTo(5.8579f, 17.5f, 2.5f, 14.1422f, 2.5f, 10.0f)
                    curveTo(2.5f, 5.8579f, 5.8579f, 2.5f, 10.0f, 2.5f)
                    curveTo(14.1422f, 2.5f, 17.5f, 5.8579f, 17.5f, 10.0f)
                    close()
                }
            }
        }
            .build()
        return _icGroupSelectedUncheckedDark!!
    }

private var _icGroupSelectedUncheckedDark: ImageVector? = null

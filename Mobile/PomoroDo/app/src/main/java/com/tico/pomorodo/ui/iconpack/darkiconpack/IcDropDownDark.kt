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

val DarkIconPack.IcDropDownDark: ImageVector
    get() {
        if (_icDropDownDark != null) {
            return _icDropDownDark!!
        }
        _icDropDownDark = Builder(
            name = "IcDropDownDark", defaultWidth = 15.0.dp, defaultHeight =
            15.0.dp, viewportWidth = 15.0f, viewportHeight = 15.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFF5F5F5)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(4.375f, 6.25f)
                    lineTo(7.5f, 9.375f)
                    lineTo(10.625f, 6.25f)
                    horizontalLineTo(4.375f)
                    close()
                }
            }
        }
            .build()
        return _icDropDownDark!!
    }

private var _icDropDownDark: ImageVector? = null

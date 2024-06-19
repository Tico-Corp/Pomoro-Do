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

val DarkIconPack.IcArrowFrontDark: ImageVector
    get() {
        if (_icArrowFrontDark != null) {
            return _icArrowFrontDark!!
        }
        _icArrowFrontDark = Builder(
            name = "IcArrowBackDark", defaultWidth = 19.0.dp, defaultHeight =
            19.0.dp, viewportWidth = 19.0f, viewportHeight = 19.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFF5F5F5)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(6.8008f, 13.1337f)
                    lineTo(10.4266f, 9.5f)
                    lineTo(6.8008f, 5.8663f)
                    lineTo(7.917f, 4.75f)
                    lineTo(12.667f, 9.5f)
                    lineTo(7.917f, 14.25f)
                    lineTo(6.8008f, 13.1337f)
                    close()
                }
            }
        }
            .build()
        return _icArrowFrontDark!!
    }

private var _icArrowFrontDark: ImageVector? = null

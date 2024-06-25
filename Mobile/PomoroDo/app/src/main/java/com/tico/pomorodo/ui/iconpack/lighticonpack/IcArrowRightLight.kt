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

val LightIconPack.IcArrowRightLight: ImageVector
    get() {
        if (_icArrowRightLight != null) {
            return _icArrowRightLight!!
        }
        _icArrowRightLight = Builder(
            name = "IcArrowRightLight", defaultWidth = 15.0.dp,
            defaultHeight = 15.0.dp, viewportWidth = 15.0f, viewportHeight = 15.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(6.25f, 10.625f)
                    lineTo(9.375f, 7.5f)
                    lineTo(6.25f, 4.375f)
                    verticalLineTo(10.625f)
                    close()
                }
            }
        }
            .build()
        return _icArrowRightLight!!
    }

private var _icArrowRightLight: ImageVector? = null

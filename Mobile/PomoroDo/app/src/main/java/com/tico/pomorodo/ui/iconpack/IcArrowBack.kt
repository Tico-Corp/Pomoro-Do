package com.tico.pomorodo.ui.iconpack

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
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcArrowBack: ImageVector
    get() {
        if (_icArrowBack != null) {
            return _icArrowBack!!
        }
        _icArrowBack = Builder(
            name = "IcArrowBack", defaultWidth = 19.0.dp, defaultHeight =
            19.0.dp, viewportWidth = 19.0f, viewportHeight = 19.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(12.1993f, 13.1338f)
                    lineTo(8.5734f, 9.5f)
                    lineTo(12.1993f, 5.8663f)
                    lineTo(11.083f, 4.75f)
                    lineTo(6.333f, 9.5f)
                    lineTo(11.083f, 14.25f)
                    lineTo(12.1993f, 13.1338f)
                    close()
                }
            }
        }
            .build()
        return _icArrowBack!!
    }

private var _icArrowBack: ImageVector? = null

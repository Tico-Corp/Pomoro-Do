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

val LightIconPack.IcMoreInfoLight: ImageVector
    get() {
        if (_icMoreInfoLight != null) {
            return _icMoreInfoLight!!
        }
        _icMoreInfoLight = Builder(
            name = "IcMoreInfoLight", defaultWidth = 28.0.dp, defaultHeight =
            28.0.dp, viewportWidth = 28.0f, viewportHeight = 28.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(14.0f, 9.3332f)
                    curveTo(15.2833f, 9.3332f, 16.3333f, 8.2832f, 16.3333f, 6.9998f)
                    curveTo(16.3333f, 5.7165f, 15.2833f, 4.6665f, 14.0f, 4.6665f)
                    curveTo(12.7166f, 4.6665f, 11.6666f, 5.7165f, 11.6666f, 6.9998f)
                    curveTo(11.6666f, 8.2832f, 12.7166f, 9.3332f, 14.0f, 9.3332f)
                    close()
                    moveTo(14.0f, 11.6665f)
                    curveTo(12.7166f, 11.6665f, 11.6666f, 12.7165f, 11.6666f, 13.9998f)
                    curveTo(11.6666f, 15.2832f, 12.7166f, 16.3332f, 14.0f, 16.3332f)
                    curveTo(15.2833f, 16.3332f, 16.3333f, 15.2832f, 16.3333f, 13.9998f)
                    curveTo(16.3333f, 12.7165f, 15.2833f, 11.6665f, 14.0f, 11.6665f)
                    close()
                    moveTo(14.0f, 18.6665f)
                    curveTo(12.7166f, 18.6665f, 11.6666f, 19.7165f, 11.6666f, 20.9998f)
                    curveTo(11.6666f, 22.2832f, 12.7166f, 23.3332f, 14.0f, 23.3332f)
                    curveTo(15.2833f, 23.3332f, 16.3333f, 22.2832f, 16.3333f, 20.9998f)
                    curveTo(16.3333f, 19.7165f, 15.2833f, 18.6665f, 14.0f, 18.6665f)
                    close()
                }
            }
        }.build()
        return _icMoreInfoLight!!
    }

private var _icMoreInfoLight: ImageVector? = null

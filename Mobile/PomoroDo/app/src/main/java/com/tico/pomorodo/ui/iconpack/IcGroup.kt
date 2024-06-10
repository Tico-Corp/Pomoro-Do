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

val IconPack.IcGroup: ImageVector
    get() {
        if (_icGroup != null) {
            return _icGroup!!
        }
        _icGroup = Builder(
            name = "IcGroup", defaultWidth = 15.0.dp, defaultHeight = 15.0.dp,
            viewportWidth = 15.0f, viewportHeight = 15.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFFFE300)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(5.625f, 7.3438f)
                    curveTo(5.1937f, 7.3438f, 4.8438f, 7.6937f, 4.8438f, 8.125f)
                    curveTo(4.8438f, 8.5562f, 5.1937f, 8.9063f, 5.625f, 8.9063f)
                    curveTo(6.0563f, 8.9063f, 6.4063f, 8.5562f, 6.4063f, 8.125f)
                    curveTo(6.4063f, 7.6937f, 6.0563f, 7.3438f, 5.625f, 7.3438f)
                    close()
                    moveTo(9.375f, 7.3438f)
                    curveTo(8.9438f, 7.3438f, 8.5938f, 7.6937f, 8.5938f, 8.125f)
                    curveTo(8.5938f, 8.5562f, 8.9438f, 8.9063f, 9.375f, 8.9063f)
                    curveTo(9.8062f, 8.9063f, 10.1562f, 8.5562f, 10.1562f, 8.125f)
                    curveTo(10.1562f, 7.6937f, 9.8062f, 7.3438f, 9.375f, 7.3438f)
                    close()
                    moveTo(7.5f, 1.25f)
                    curveTo(4.05f, 1.25f, 1.25f, 4.05f, 1.25f, 7.5f)
                    curveTo(1.25f, 10.95f, 4.05f, 13.75f, 7.5f, 13.75f)
                    curveTo(10.95f, 13.75f, 13.75f, 10.95f, 13.75f, 7.5f)
                    curveTo(13.75f, 4.05f, 10.95f, 1.25f, 7.5f, 1.25f)
                    close()
                    moveTo(7.5f, 12.5f)
                    curveTo(4.7438f, 12.5f, 2.5f, 10.2562f, 2.5f, 7.5f)
                    curveTo(2.5f, 7.3187f, 2.5125f, 7.1375f, 2.5313f, 6.9625f)
                    curveTo(4.0062f, 6.3063f, 5.175f, 5.1f, 5.7875f, 3.6063f)
                    curveTo(6.9187f, 5.2063f, 8.7813f, 6.25f, 10.8875f, 6.25f)
                    curveTo(11.375f, 6.25f, 11.8437f, 6.1937f, 12.2937f, 6.0875f)
                    curveTo(12.425f, 6.5313f, 12.5f, 7.0062f, 12.5f, 7.5f)
                    curveTo(12.5f, 10.2562f, 10.2562f, 12.5f, 7.5f, 12.5f)
                    close()
                }
            }
        }
            .build()
        return _icGroup!!
    }

private var _icGroup: ImageVector? = null

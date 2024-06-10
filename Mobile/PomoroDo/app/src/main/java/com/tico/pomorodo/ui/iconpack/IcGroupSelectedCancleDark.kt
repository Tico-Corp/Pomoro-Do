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

val IconPack.IcGroupSelectedCancleDark: ImageVector
    get() {
        if (_icGroupSelectedCancleDark != null) {
            return _icGroupSelectedCancleDark!!
        }
        _icGroupSelectedCancleDark = Builder(
            name = "IcGroupSelectedCancleDark", defaultWidth =
            13.0.dp, defaultHeight = 13.0.dp, viewportWidth = 13.0f, viewportHeight =
            13.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFD9D9D9)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(6.5f, 0.0f)
                    curveTo(2.9158f, 0.0f, 0.0f, 2.916f, 0.0f, 6.5004f)
                    curveTo(0.0f, 10.0842f, 2.9158f, 13.0f, 6.5f, 13.0f)
                    curveTo(10.0842f, 13.0f, 12.9999f, 10.0842f, 12.9999f, 6.5004f)
                    curveTo(12.9999f, 2.916f, 10.0842f, 0.0f, 6.5f, 0.0f)
                    close()
                    moveTo(9.4909f, 9.1884f)
                    lineTo(9.1981f, 9.4814f)
                    curveTo(9.1242f, 9.5561f, 8.9921f, 9.5561f, 8.9177f, 9.4814f)
                    lineTo(6.5815f, 7.1453f)
                    curveTo(6.5623f, 7.1259f, 6.5366f, 7.1154f, 6.5095f, 7.1154f)
                    curveTo(6.4824f, 7.1154f, 6.4567f, 7.1259f, 6.4375f, 7.1453f)
                    lineTo(4.1014f, 9.4815f)
                    curveTo(4.0271f, 9.5562f, 3.8951f, 9.5562f, 3.8208f, 9.4815f)
                    lineTo(3.5282f, 9.1885f)
                    curveTo(3.491f, 9.1516f, 3.4704f, 9.1015f, 3.4704f, 9.0489f)
                    curveTo(3.4704f, 8.9957f, 3.491f, 8.9458f, 3.5282f, 8.9081f)
                    lineTo(5.8645f, 6.5721f)
                    curveTo(5.9042f, 6.5322f, 5.9042f, 6.4681f, 5.8645f, 6.4285f)
                    lineTo(3.5282f, 4.0922f)
                    curveTo(3.491f, 4.0549f, 3.4704f, 4.005f, 3.4704f, 3.952f)
                    curveTo(3.4704f, 3.8988f, 3.491f, 3.8489f, 3.5282f, 3.8117f)
                    lineTo(3.8208f, 3.519f)
                    curveTo(3.8951f, 3.4447f, 4.0271f, 3.4447f, 4.1014f, 3.519f)
                    lineTo(6.4376f, 5.8551f)
                    curveTo(6.4757f, 5.8931f, 6.5434f, 5.8931f, 6.5815f, 5.8551f)
                    lineTo(8.9176f, 3.5189f)
                    curveTo(8.992f, 3.4445f, 9.1241f, 3.4445f, 9.198f, 3.5189f)
                    lineTo(9.4908f, 3.8117f)
                    curveTo(9.5679f, 3.8887f, 9.5679f, 4.0149f, 9.4908f, 4.0922f)
                    lineTo(7.1542f, 6.4283f)
                    curveTo(7.1146f, 6.468f, 7.1146f, 6.5321f, 7.1542f, 6.572f)
                    lineTo(9.4909f, 8.908f)
                    curveTo(9.568f, 8.9855f, 9.568f, 9.1112f, 9.4909f, 9.1884f)
                    close()
                }
            }
        }
            .build()
        return _icGroupSelectedCancleDark!!
    }

private var _icGroupSelectedCancleDark: ImageVector? = null

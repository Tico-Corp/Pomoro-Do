package com.tico.pomorodo.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcTimelineOrangeFilled: ImageVector
    get() {
        if (_icTimelineOrangeFilled != null) {
            return _icTimelineOrangeFilled!!
        }
        _icTimelineOrangeFilled = Builder(
            name = "IcTimelineOrangeFilled", defaultWidth = 16.0.dp,
            defaultHeight = 16.0.dp, viewportWidth = 16.0f, viewportHeight = 16.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFF9A40)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.314f, 14.6626f)
                curveTo(11.3462f, 14.6626f, 13.8042f, 12.2045f, 13.8042f, 9.1724f)
                curveTo(13.8042f, 6.1402f, 11.3462f, 3.6821f, 8.314f, 3.6821f)
                curveTo(5.2818f, 3.6821f, 2.8237f, 6.1402f, 2.8237f, 9.1724f)
                curveTo(2.8237f, 12.2045f, 5.2818f, 14.6626f, 8.314f, 14.6626f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFC7D333)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(6.3714f, 1.2461f)
                curveTo(6.472f, 1.0957f, 6.628f, 0.9911f, 6.8053f, 0.9551f)
                curveTo(6.9827f, 0.9191f, 7.1671f, 0.9547f, 7.3184f, 1.054f)
                curveTo(7.96f, 1.5173f, 8.4401f, 2.1702f, 8.691f, 2.9207f)
                curveTo(8.972f, 2.6818f, 9.3021f, 2.5077f, 9.6579f, 2.4104f)
                curveTo(10.0136f, 2.3132f, 10.3864f, 2.2953f, 10.7498f, 2.3579f)
                curveTo(12.2871f, 2.6736f, 13.495f, 4.2932f, 13.6322f, 4.4785f)
                curveTo(13.708f, 4.5844f, 13.7522f, 4.7097f, 13.7595f, 4.8397f)
                curveTo(13.7668f, 4.9697f, 13.7369f, 5.0992f, 13.6734f, 5.2128f)
                curveTo(13.2959f, 5.9609f, 12.3008f, 7.1138f, 11.0587f, 7.1138f)
                curveTo(10.9181f, 7.1134f, 10.778f, 7.0996f, 10.64f, 7.0727f)
                curveTo(9.0822f, 6.7501f, 7.8949f, 4.9452f, 7.7371f, 4.7462f)
                curveTo(7.6678f, 4.6323f, 7.6299f, 4.502f, 7.6273f, 4.3687f)
                curveTo(7.6221f, 3.9499f, 7.5241f, 3.5375f, 7.3401f, 3.1613f)
                curveTo(7.1561f, 2.7851f, 6.8909f, 2.4544f, 6.5635f, 2.1932f)
                curveTo(6.4131f, 2.0926f, 6.3085f, 1.9366f, 6.2725f, 1.7592f)
                curveTo(6.2365f, 1.5819f, 6.272f, 1.3974f, 6.3714f, 1.2461f)
                close()
            }
        }
            .build()
        return _icTimelineOrangeFilled!!
    }

private var _icTimelineOrangeFilled: ImageVector? = null

package com.tico.pomorodo.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcTimelineOrangeOutline: ImageVector
    get() {
        if (_icTimelineOrangeOutline != null) {
            return _icTimelineOrangeOutline!!
        }
        _icTimelineOrangeOutline = Builder(
            name = "IcTimelineOrangeOutline", defaultWidth = 16.0.dp,
            defaultHeight = 16.0.dp, viewportWidth = 16.0f, viewportHeight = 16.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFF9A40)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(7.5474f, 3.7353f)
                curveTo(4.8781f, 4.1083f, 2.8237f, 6.4005f, 2.8237f, 9.1725f)
                curveTo(2.8237f, 12.2047f, 5.2818f, 14.6627f, 8.314f, 14.6627f)
                curveTo(11.3462f, 14.6627f, 13.8042f, 12.2047f, 13.8042f, 9.1725f)
                curveTo(13.8042f, 8.0829f, 13.4868f, 7.0675f, 12.9395f, 6.2136f)
                curveTo(12.7082f, 6.4465f, 12.4393f, 6.6614f, 12.1411f, 6.8229f)
                curveTo(12.5617f, 7.5064f, 12.8042f, 8.3111f, 12.8042f, 9.1725f)
                curveTo(12.8042f, 11.6524f, 10.7939f, 13.6627f, 8.314f, 13.6627f)
                curveTo(5.8341f, 13.6627f, 3.8237f, 11.6524f, 3.8237f, 9.1725f)
                curveTo(3.8237f, 6.8932f, 5.522f, 5.0105f, 7.7223f, 4.7209f)
                curveTo(7.6624f, 4.6132f, 7.6297f, 4.4924f, 7.6273f, 4.3689f)
                curveTo(7.6246f, 4.1547f, 7.5977f, 3.9422f, 7.5474f, 3.7353f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF007E14)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(9.7897f, 2.8927f)
                curveTo(10.0723f, 2.8155f, 10.3683f, 2.8007f, 10.6571f, 2.8493f)
                curveTo(11.2935f, 2.9824f, 11.8962f, 3.395f, 12.3786f, 3.8361f)
                curveTo(12.8476f, 4.265f, 13.1593f, 4.6802f, 13.2273f, 4.7719f)
                curveTo(13.2469f, 4.8002f, 13.2583f, 4.8333f, 13.2603f, 4.8677f)
                curveTo(13.2622f, 4.903f, 13.2541f, 4.9381f, 13.2369f, 4.969f)
                lineTo(13.2318f, 4.9782f)
                lineTo(13.227f, 4.9876f)
                curveTo(13.0662f, 5.3062f, 12.7637f, 5.7269f, 12.3674f, 6.0621f)
                curveTo(11.9707f, 6.3977f, 11.5222f, 6.6136f, 11.0594f, 6.6138f)
                curveTo(10.9517f, 6.6135f, 10.8444f, 6.603f, 10.7387f, 6.5825f)
                curveTo(10.1053f, 6.4504f, 9.5057f, 6.0031f, 9.0189f, 5.5113f)
                curveTo(8.6323f, 5.1209f, 8.366f, 4.7571f, 8.2187f, 4.5559f)
                curveTo(8.194f, 4.5222f, 8.1727f, 4.493f, 8.1546f, 4.4691f)
                curveTo(8.1376f, 4.4353f, 8.1281f, 4.3981f, 8.1272f, 4.3602f)
                curveTo(8.1208f, 3.8682f, 8.0054f, 3.3837f, 7.7893f, 2.9417f)
                curveTo(7.5728f, 2.4989f, 7.2606f, 2.1098f, 6.8754f, 1.8024f)
                lineTo(6.859f, 1.7893f)
                lineTo(6.8415f, 1.7776f)
                curveTo(6.8007f, 1.7503f, 6.7723f, 1.708f, 6.7625f, 1.6598f)
                curveTo(6.7529f, 1.6124f, 6.7621f, 1.5631f, 6.7882f, 1.5223f)
                curveTo(6.8155f, 1.4825f, 6.8573f, 1.4548f, 6.9048f, 1.4451f)
                curveTo(6.9503f, 1.4359f, 6.9975f, 1.444f, 7.0373f, 1.4677f)
                curveTo(7.5882f, 1.8692f, 8.0006f, 2.4324f, 8.2168f, 3.0792f)
                lineTo(8.4513f, 3.7805f)
                lineTo(9.0148f, 3.3017f)
                curveTo(9.24f, 3.1103f, 9.5046f, 2.9707f, 9.7897f, 2.8927f)
                close()
            }
        }
            .build()
        return _icTimelineOrangeOutline!!
    }

private var _icTimelineOrangeOutline: ImageVector? = null

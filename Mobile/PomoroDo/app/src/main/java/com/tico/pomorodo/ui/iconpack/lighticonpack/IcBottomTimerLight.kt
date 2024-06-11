package com.tico.pomorodo.ui.iconpack.lighticonpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.LightIconPack

val LightIconPack.IcBottomTimerLight: ImageVector
    get() {
        if (_icBottomTimerLight != null) {
            return _icBottomTimerLight!!
        }
        _icBottomTimerLight = Builder(
            name = "IcBottomTimerLight", defaultWidth = 21.0.dp,
            defaultHeight = 20.0.dp, viewportWidth = 21.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF241912)),
                strokeLineWidth = 1.13661f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(16.9228f, 3.2778f)
                verticalLineTo(6.0556f)
                horizontalLineTo(14.0813f)
                moveTo(10.6714f, 18.0f)
                curveTo(9.3607f, 17.9999f, 8.0718f, 17.6715f, 6.9278f, 17.0462f)
                curveTo(5.7838f, 16.4208f, 4.8227f, 15.5194f, 4.1363f, 14.4278f)
                curveTo(3.4498f, 13.3363f, 3.0609f, 12.091f, 3.0066f, 10.8108f)
                curveTo(2.9523f, 9.5305f, 3.2344f, 8.258f, 3.826f, 7.1147f)
                curveTo(4.4177f, 5.9713f, 5.2991f, 4.9952f, 6.3862f, 4.2794f)
                curveTo(7.4734f, 3.5636f, 8.73f, 3.132f, 10.0362f, 3.0258f)
                curveTo(11.3424f, 2.9196f, 12.6547f, 3.1423f, 13.8479f, 3.6727f)
                curveTo(15.041f, 4.2031f, 16.0753f, 5.0234f, 16.852f, 6.0556f)
                moveTo(14.3611f, 17.0772f)
                curveTo(13.738f, 17.4122f, 13.0699f, 17.6603f, 12.3763f, 17.8142f)
                moveTo(16.852f, 14.9444f)
                curveTo(16.4667f, 15.4556f, 16.0169f, 15.9171f, 15.5134f, 16.3181f)
                moveTo(18.0f, 12.7258f)
                curveTo(17.8803f, 13.1028f, 17.7302f, 13.4699f, 17.551f, 13.8236f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF241912)),
                strokeLineWidth = 1.13661f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(10.6728f, 11.3329f)
                curveTo(11.1436f, 11.3329f, 11.5252f, 10.9598f, 11.5252f, 10.4996f)
                curveTo(11.5252f, 10.0394f, 11.1436f, 9.6663f, 10.6728f, 9.6663f)
                curveTo(10.202f, 9.6663f, 9.8203f, 10.0394f, 9.8203f, 10.4996f)
                curveTo(9.8203f, 10.9598f, 10.202f, 11.3329f, 10.6728f, 11.3329f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF241912)),
                strokeLineWidth = 1.13661f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(11.2296f, 11.1279f)
                lineTo(14.0822f, 13.2777f)
                moveTo(10.6724f, 4.9443f)
                verticalLineTo(9.6666f)
            }
        }
            .build()
        return _icBottomTimerLight!!
    }

private var _icBottomTimerLight: ImageVector? = null

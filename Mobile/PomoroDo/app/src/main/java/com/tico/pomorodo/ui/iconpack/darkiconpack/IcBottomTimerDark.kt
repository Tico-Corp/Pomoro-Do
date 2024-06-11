package com.tico.pomorodo.ui.iconpack.darkiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.DarkIconPack

val DarkIconPack.IcBottomTimerDark: ImageVector
    get() {
        if (_icBottomTimerDark != null) {
            return _icBottomTimerDark!!
        }
        _icBottomTimerDark = Builder(
            name = "IcBottomTimerDark", defaultWidth = 20.0.dp,
            defaultHeight = 20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFF5F5F5)),
                strokeLineWidth = 1.13661f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(16.4228f, 3.2778f)
                verticalLineTo(6.0556f)
                horizontalLineTo(13.5813f)
                moveTo(10.1714f, 18.0f)
                curveTo(8.8607f, 17.9999f, 7.5718f, 17.6715f, 6.4278f, 17.0462f)
                curveTo(5.2838f, 16.4208f, 4.3227f, 15.5194f, 3.6363f, 14.4278f)
                curveTo(2.9498f, 13.3363f, 2.5609f, 12.091f, 2.5066f, 10.8108f)
                curveTo(2.4523f, 9.5305f, 2.7344f, 8.258f, 3.326f, 7.1147f)
                curveTo(3.9177f, 5.9713f, 4.7991f, 4.9952f, 5.8862f, 4.2794f)
                curveTo(6.9734f, 3.5636f, 8.23f, 3.132f, 9.5362f, 3.0258f)
                curveTo(10.8424f, 2.9196f, 12.1547f, 3.1423f, 13.3479f, 3.6727f)
                curveTo(14.541f, 4.2031f, 15.5753f, 5.0234f, 16.352f, 6.0556f)
                moveTo(13.8611f, 17.0772f)
                curveTo(13.238f, 17.4122f, 12.5699f, 17.6603f, 11.8763f, 17.8142f)
                moveTo(16.352f, 14.9444f)
                curveTo(15.9667f, 15.4556f, 15.5169f, 15.9171f, 15.0134f, 16.3181f)
                moveTo(17.5f, 12.7258f)
                curveTo(17.3803f, 13.1028f, 17.2302f, 13.4699f, 17.051f, 13.8236f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFF5F5F5)),
                strokeLineWidth = 1.13661f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(10.1728f, 11.3329f)
                curveTo(10.6436f, 11.3329f, 11.0252f, 10.9598f, 11.0252f, 10.4996f)
                curveTo(11.0252f, 10.0394f, 10.6436f, 9.6663f, 10.1728f, 9.6663f)
                curveTo(9.702f, 9.6663f, 9.3203f, 10.0394f, 9.3203f, 10.4996f)
                curveTo(9.3203f, 10.9598f, 9.702f, 11.3329f, 10.1728f, 11.3329f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFF5F5F5)),
                strokeLineWidth = 1.13661f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(10.7296f, 11.1279f)
                lineTo(13.5822f, 13.2777f)
                moveTo(10.1724f, 4.9443f)
                verticalLineTo(9.6666f)
            }
        }
            .build()
        return _icBottomTimerDark!!
    }

private var _icBottomTimerDark: ImageVector? = null

package com.tico.pomorodo.ui.iconpack.commonIconPack

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

val IconPack.IcSetting: ImageVector
    get() {
        if (_icSetting != null) {
            return _icSetting!!
        }
        _icSetting = Builder(
            name = "IcSetting", defaultWidth = 28.0.dp, defaultHeight = 28.0.dp,
            viewportWidth = 28.0f, viewportHeight = 28.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF898989)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(22.3298f, 15.0965f)
                    curveTo(22.3765f, 14.7465f, 22.3998f, 14.3848f, 22.3998f, 13.9998f)
                    curveTo(22.3998f, 13.6265f, 22.3765f, 13.2531f, 22.3181f, 12.9031f)
                    lineTo(24.6865f, 11.0598f)
                    curveTo(24.8965f, 10.8965f, 24.9548f, 10.5815f, 24.8265f, 10.3481f)
                    lineTo(22.5865f, 6.4748f)
                    curveTo(22.4465f, 6.2181f, 22.1548f, 6.1365f, 21.8981f, 6.2181f)
                    lineTo(19.1098f, 7.3381f)
                    curveTo(18.5265f, 6.8948f, 17.9081f, 6.5215f, 17.2198f, 6.2415f)
                    lineTo(16.7998f, 3.2781f)
                    curveTo(16.7531f, 2.9981f, 16.5198f, 2.7998f, 16.2398f, 2.7998f)
                    horizontalLineTo(11.7598f)
                    curveTo(11.4798f, 2.7998f, 11.2581f, 2.9981f, 11.2115f, 3.2781f)
                    lineTo(10.7915f, 6.2415f)
                    curveTo(10.1031f, 6.5215f, 9.4731f, 6.9065f, 8.9015f, 7.3381f)
                    lineTo(6.1131f, 6.2181f)
                    curveTo(5.8565f, 6.1248f, 5.5648f, 6.2181f, 5.4248f, 6.4748f)
                    lineTo(3.1965f, 10.3481f)
                    curveTo(3.0565f, 10.5931f, 3.1031f, 10.8965f, 3.3365f, 11.0598f)
                    lineTo(5.7048f, 12.9031f)
                    curveTo(5.6465f, 13.2531f, 5.5998f, 13.6381f, 5.5998f, 13.9998f)
                    curveTo(5.5998f, 14.3615f, 5.6231f, 14.7465f, 5.6815f, 15.0965f)
                    lineTo(3.3131f, 16.9398f)
                    curveTo(3.1031f, 17.1031f, 3.0448f, 17.4181f, 3.1731f, 17.6515f)
                    lineTo(5.4131f, 21.5248f)
                    curveTo(5.5531f, 21.7815f, 5.8448f, 21.8631f, 6.1015f, 21.7815f)
                    lineTo(8.8898f, 20.6615f)
                    curveTo(9.4731f, 21.1048f, 10.0915f, 21.4781f, 10.7798f, 21.7581f)
                    lineTo(11.1998f, 24.7215f)
                    curveTo(11.2581f, 25.0015f, 11.4798f, 25.1998f, 11.7598f, 25.1998f)
                    horizontalLineTo(16.2398f)
                    curveTo(16.5198f, 25.1998f, 16.7531f, 25.0015f, 16.7881f, 24.7215f)
                    lineTo(17.2081f, 21.7581f)
                    curveTo(17.8965f, 21.4781f, 18.5265f, 21.1048f, 19.0981f, 20.6615f)
                    lineTo(21.8865f, 21.7815f)
                    curveTo(22.1431f, 21.8748f, 22.4348f, 21.7815f, 22.5748f, 21.5248f)
                    lineTo(24.8148f, 17.6515f)
                    curveTo(24.9548f, 17.3948f, 24.8965f, 17.1031f, 24.6748f, 16.9398f)
                    lineTo(22.3298f, 15.0965f)
                    close()
                    moveTo(13.9998f, 18.1998f)
                    curveTo(11.6898f, 18.1998f, 9.7998f, 16.3098f, 9.7998f, 13.9998f)
                    curveTo(9.7998f, 11.6898f, 11.6898f, 9.7998f, 13.9998f, 9.7998f)
                    curveTo(16.3098f, 9.7998f, 18.1998f, 11.6898f, 18.1998f, 13.9998f)
                    curveTo(18.1998f, 16.3098f, 16.3098f, 18.1998f, 13.9998f, 18.1998f)
                    close()
                }
            }
        }
            .build()
        return _icSetting!!
    }

private var _icSetting: ImageVector? = null

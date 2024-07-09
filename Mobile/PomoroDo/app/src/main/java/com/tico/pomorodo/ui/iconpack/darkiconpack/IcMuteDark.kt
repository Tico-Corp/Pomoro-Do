package com.tico.pomorodo.ui.iconpack.darkiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.DarkIconPack

val DarkIconPack.IcMuteDark: ImageVector
    get() {
        if (_icMuteDark != null) {
            return _icMuteDark!!
        }
        _icMuteDark = Builder(
            name = "IcMuteDark", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
            viewportWidth = 16.0f, viewportHeight = 16.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFDFDFD)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(12.0f, 6.4981f)
                    verticalLineTo(9.498f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFDFDFD)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(14.0f, 5.4981f)
                    verticalLineTo(10.498f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFDFDFD)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(2.9995f, 2.4971f)
                    lineTo(12.9995f, 13.4971f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFDFDFD)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(7.0122f, 3.9346f)
                    lineTo(9.4997f, 1.9971f)
                    verticalLineTo(6.6721f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFDFDFD)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(9.5f, 9.6481f)
                    verticalLineTo(13.998f)
                    lineTo(5.0f, 10.498f)
                    horizontalLineTo(2.0f)
                    curveTo(1.8674f, 10.498f, 1.7402f, 10.4454f, 1.6465f, 10.3516f)
                    curveTo(1.5527f, 10.2578f, 1.5f, 10.1307f, 1.5f, 9.998f)
                    verticalLineTo(5.9981f)
                    curveTo(1.5f, 5.8654f, 1.5527f, 5.7383f, 1.6465f, 5.6445f)
                    curveTo(1.7402f, 5.5507f, 1.8674f, 5.4981f, 2.0f, 5.4981f)
                    horizontalLineTo(5.725f)
                }
            }
        }
            .build()
        return _icMuteDark!!
    }

private var _icMuteDark: ImageVector? = null

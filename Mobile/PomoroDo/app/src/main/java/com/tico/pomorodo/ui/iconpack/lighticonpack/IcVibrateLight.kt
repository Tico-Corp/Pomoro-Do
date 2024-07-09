package com.tico.pomorodo.ui.iconpack.lighticonpack

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
import com.tico.pomorodo.ui.theme.LightIconPack

val LightIconPack.IcVibrateLight: ImageVector
    get() {
        if (_icVibrateLight != null) {
            return _icVibrateLight!!
        }
        _icVibrateLight = Builder(
            name = "IcVibrateLight", defaultWidth = 16.0.dp, defaultHeight =
            16.0.dp, viewportWidth = 16.0f, viewportHeight = 16.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(11.0f, 12.501f)
                    verticalLineTo(3.501f)
                    curveTo(11.0f, 2.9487f, 10.5523f, 2.501f, 10.0f, 2.501f)
                    lineTo(6.0f, 2.501f)
                    curveTo(5.4477f, 2.501f, 5.0f, 2.9487f, 5.0f, 3.501f)
                    lineTo(5.0f, 12.501f)
                    curveTo(5.0f, 13.0533f, 5.4477f, 13.501f, 6.0f, 13.501f)
                    horizontalLineTo(10.0f)
                    curveTo(10.5523f, 13.501f, 11.0f, 13.0533f, 11.0f, 12.501f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(13.0005f, 5.5019f)
                    verticalLineTo(10.502f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(14.9995f, 6.499f)
                    verticalLineTo(9.499f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(2.9995f, 5.5019f)
                    verticalLineTo(10.502f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(1.0005f, 6.499f)
                    verticalLineTo(9.499f)
                }
            }
        }
            .build()
        return _icVibrateLight!!
    }

private var _icVibrateLight: ImageVector? = null

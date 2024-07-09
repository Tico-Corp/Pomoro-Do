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

val LightIconPack.IcSoundLight: ImageVector
    get() {
        if (_icSoundLight != null) {
            return _icSoundLight!!
        }
        _icSoundLight = Builder(
            name = "IcSoundLight", defaultWidth = 16.0.dp, defaultHeight =
            16.0.dp, viewportWidth = 16.0f, viewportHeight = 16.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(5.0f, 10.5f)
                    horizontalLineTo(2.0f)
                    curveTo(1.8674f, 10.5f, 1.7402f, 10.4473f, 1.6465f, 10.3536f)
                    curveTo(1.5527f, 10.2598f, 1.5f, 10.1326f, 1.5f, 10.0f)
                    verticalLineTo(6.0f)
                    curveTo(1.5f, 5.8674f, 1.5527f, 5.7402f, 1.6465f, 5.6465f)
                    curveTo(1.7402f, 5.5527f, 1.8674f, 5.5f, 2.0f, 5.5f)
                    horizontalLineTo(5.0f)
                    lineTo(9.5f, 2.0f)
                    verticalLineTo(14.0f)
                    lineTo(5.0f, 10.5f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(12.0f, 6.5f)
                    verticalLineTo(9.5f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF272A00)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
                ) {
                    moveTo(14.001f, 5.4981f)
                    verticalLineTo(10.498f)
                }
            }
        }
            .build()
        return _icSoundLight!!
    }

private var _icSoundLight: ImageVector? = null

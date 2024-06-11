package com.tico.pomorodo.ui.iconpack.lighticonpack

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
import com.tico.pomorodo.ui.theme.LightIconPack

val LightIconPack.IcAddCategory: ImageVector
    get() {
        if (_icAddCategory != null) {
            return _icAddCategory!!
        }
        _icAddCategory = Builder(
            name = "IcAddCategory", defaultWidth = 14.0.dp, defaultHeight =
            14.0.dp, viewportWidth = 14.0f, viewportHeight = 14.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(11.0827f, 7.5834f)
                    horizontalLineTo(7.5827f)
                    verticalLineTo(11.0834f)
                    horizontalLineTo(6.416f)
                    verticalLineTo(7.5834f)
                    horizontalLineTo(2.916f)
                    verticalLineTo(6.4167f)
                    horizontalLineTo(6.416f)
                    verticalLineTo(2.9167f)
                    horizontalLineTo(7.5827f)
                    verticalLineTo(6.4167f)
                    horizontalLineTo(11.0827f)
                    verticalLineTo(7.5834f)
                    close()
                }
            }
        }
            .build()
        return _icAddCategory!!
    }

private var _icAddCategory: ImageVector? = null

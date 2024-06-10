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

val IconPack.IcTodoUncheckedLight: ImageVector
    get() {
        if (_icTodoUncheckedLight != null) {
            return _icTodoUncheckedLight!!
        }
        _icTodoUncheckedLight = Builder(
            name = "IcTodoUncheckedLight", defaultWidth = 96.0.dp,
            defaultHeight = 96.0.dp, viewportWidth = 96.0f, viewportHeight = 96.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(76.0f, 20.0f)
                    verticalLineTo(76.0f)
                    horizontalLineTo(20.0f)
                    verticalLineTo(20.0f)
                    horizontalLineTo(76.0f)
                    close()
                    moveTo(76.0f, 12.0f)
                    horizontalLineTo(20.0f)
                    curveTo(15.6f, 12.0f, 12.0f, 15.6f, 12.0f, 20.0f)
                    verticalLineTo(76.0f)
                    curveTo(12.0f, 80.4f, 15.6f, 84.0f, 20.0f, 84.0f)
                    horizontalLineTo(76.0f)
                    curveTo(80.4f, 84.0f, 84.0f, 80.4f, 84.0f, 76.0f)
                    verticalLineTo(20.0f)
                    curveTo(84.0f, 15.6f, 80.4f, 12.0f, 76.0f, 12.0f)
                    close()
                }
            }
        }
            .build()
        return _icTodoUncheckedLight!!
    }

private var _icTodoUncheckedLight: ImageVector? = null

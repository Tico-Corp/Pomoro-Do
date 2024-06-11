package com.tico.pomorodo.ui.iconpack.darkiconpack

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
import com.tico.pomorodo.ui.theme.DarkIconPack

val DarkIconPack.IcCategoryLockDark: ImageVector
    get() {
        if (_icCategoryLockDark != null) {
            return _icCategoryLockDark!!
        }
        _icCategoryLockDark = Builder(
            name = "IcCategoryLockDark", defaultWidth = 16.0.dp,
            defaultHeight = 16.0.dp, viewportWidth = 16.0f, viewportHeight = 16.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFF5F5F5)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(11.9993f, 5.3333f)
                    horizontalLineTo(11.3327f)
                    verticalLineTo(4.0f)
                    curveTo(11.3327f, 2.16f, 9.8393f, 0.6667f, 7.9994f, 0.6667f)
                    curveTo(6.1593f, 0.6667f, 4.666f, 2.16f, 4.666f, 4.0f)
                    verticalLineTo(5.3333f)
                    horizontalLineTo(3.9994f)
                    curveTo(3.266f, 5.3333f, 2.666f, 5.9333f, 2.666f, 6.6667f)
                    verticalLineTo(13.3333f)
                    curveTo(2.666f, 14.0667f, 3.266f, 14.6667f, 3.9994f, 14.6667f)
                    horizontalLineTo(11.9993f)
                    curveTo(12.7327f, 14.6667f, 13.3327f, 14.0667f, 13.3327f, 13.3333f)
                    verticalLineTo(6.6667f)
                    curveTo(13.3327f, 5.9333f, 12.7327f, 5.3333f, 11.9993f, 5.3333f)
                    close()
                    moveTo(7.9994f, 11.3333f)
                    curveTo(7.266f, 11.3333f, 6.666f, 10.7333f, 6.666f, 10.0f)
                    curveTo(6.666f, 9.2667f, 7.266f, 8.6667f, 7.9994f, 8.6667f)
                    curveTo(8.7327f, 8.6667f, 9.3327f, 9.2667f, 9.3327f, 10.0f)
                    curveTo(9.3327f, 10.7333f, 8.7327f, 11.3333f, 7.9994f, 11.3333f)
                    close()
                    moveTo(10.066f, 5.3333f)
                    horizontalLineTo(5.9327f)
                    verticalLineTo(4.0f)
                    curveTo(5.9327f, 2.86f, 6.8594f, 1.9333f, 7.9994f, 1.9333f)
                    curveTo(9.1393f, 1.9333f, 10.066f, 2.86f, 10.066f, 4.0f)
                    verticalLineTo(5.3333f)
                    close()
                }
            }
        }
            .build()
        return _icCategoryLockDark!!
    }

private var _icCategoryLockDark: ImageVector? = null

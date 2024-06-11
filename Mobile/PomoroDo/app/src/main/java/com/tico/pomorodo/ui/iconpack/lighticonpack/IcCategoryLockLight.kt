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

val LightIconPack.IcCategoryLockLight: ImageVector
    get() {
        if (_icCategoryLockLight != null) {
            return _icCategoryLockLight!!
        }
        _icCategoryLockLight = Builder(
            name = "IcCategoryLockLight", defaultWidth = 16.0.dp,
            defaultHeight = 16.0.dp, viewportWidth = 16.0f, viewportHeight = 16.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(11.9993f, 5.3332f)
                    horizontalLineTo(11.3327f)
                    verticalLineTo(3.9998f)
                    curveTo(11.3327f, 2.1598f, 9.8393f, 0.6665f, 7.9994f, 0.6665f)
                    curveTo(6.1593f, 0.6665f, 4.666f, 2.1598f, 4.666f, 3.9998f)
                    verticalLineTo(5.3332f)
                    horizontalLineTo(3.9994f)
                    curveTo(3.266f, 5.3332f, 2.666f, 5.9332f, 2.666f, 6.6665f)
                    verticalLineTo(13.3332f)
                    curveTo(2.666f, 14.0665f, 3.266f, 14.6665f, 3.9994f, 14.6665f)
                    horizontalLineTo(11.9993f)
                    curveTo(12.7327f, 14.6665f, 13.3327f, 14.0665f, 13.3327f, 13.3332f)
                    verticalLineTo(6.6665f)
                    curveTo(13.3327f, 5.9332f, 12.7327f, 5.3332f, 11.9993f, 5.3332f)
                    close()
                    moveTo(7.9994f, 11.3332f)
                    curveTo(7.266f, 11.3332f, 6.666f, 10.7332f, 6.666f, 9.9998f)
                    curveTo(6.666f, 9.2665f, 7.266f, 8.6665f, 7.9994f, 8.6665f)
                    curveTo(8.7327f, 8.6665f, 9.3327f, 9.2665f, 9.3327f, 9.9998f)
                    curveTo(9.3327f, 10.7332f, 8.7327f, 11.3332f, 7.9994f, 11.3332f)
                    close()
                    moveTo(10.066f, 5.3332f)
                    horizontalLineTo(5.9327f)
                    verticalLineTo(3.9998f)
                    curveTo(5.9327f, 2.8598f, 6.8594f, 1.9332f, 7.9994f, 1.9332f)
                    curveTo(9.1393f, 1.9332f, 10.066f, 2.8598f, 10.066f, 3.9998f)
                    verticalLineTo(5.3332f)
                    close()
                }
            }
        }
            .build()
        return _icCategoryLockLight!!
    }

private var _icCategoryLockLight: ImageVector? = null

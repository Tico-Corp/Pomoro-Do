package com.tico.pomorodo.ui.iconpack.lighticonpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
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

val LightIconPack.IcCategoryFollowerOpenLight: ImageVector
    get() {
        if (_icCategoryFollowerOpenLight != null) {
            return _icCategoryFollowerOpenLight!!
        }
        _icCategoryFollowerOpenLight = Builder(
            name = "IcCategoryFollowerOpenLight", defaultWidth =
            16.0.dp, defaultHeight = 16.0.dp, viewportWidth = 16.0f, viewportHeight =
            16.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(7.0f, 14.0001f)
                    curveTo(7.0f, 14.0001f, 6.0f, 14.0001f, 6.0f, 13.0001f)
                    curveTo(6.0f, 12.0001f, 7.0f, 9.0001f, 11.0f, 9.0001f)
                    curveTo(15.0001f, 9.0001f, 16.0001f, 12.0001f, 16.0001f, 13.0001f)
                    curveTo(16.0001f, 14.0001f, 15.0001f, 14.0001f, 15.0001f, 14.0001f)
                    horizontalLineTo(7.0f)
                    close()
                    moveTo(11.0f, 8.0001f)
                    curveTo(11.7957f, 8.0001f, 12.5588f, 7.684f, 13.1214f, 7.1214f)
                    curveTo(13.684f, 6.5588f, 14.0001f, 5.7957f, 14.0001f, 5.0f)
                    curveTo(14.0001f, 4.2044f, 13.684f, 3.4413f, 13.1214f, 2.8787f)
                    curveTo(12.5588f, 2.3161f, 11.7957f, 2.0f, 11.0f, 2.0f)
                    curveTo(10.2044f, 2.0f, 9.4413f, 2.3161f, 8.8787f, 2.8787f)
                    curveTo(8.3161f, 3.4413f, 8.0f, 4.2044f, 8.0f, 5.0f)
                    curveTo(8.0f, 5.7957f, 8.3161f, 6.5588f, 8.8787f, 7.1214f)
                    curveTo(9.4413f, 7.684f, 10.2044f, 8.0001f, 11.0f, 8.0001f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd
                ) {
                    moveTo(5.2161f, 14.0f)
                    curveTo(5.0678f, 13.6878f, 4.9939f, 13.3455f, 5.0001f, 13.0f)
                    curveTo(5.0001f, 11.645f, 5.68f, 10.25f, 6.9361f, 9.2799f)
                    curveTo(6.3092f, 9.0868f, 5.656f, 8.9923f, 5.0001f, 8.9999f)
                    curveTo(1.0f, 8.9999f, 0.0f, 12.0f, 0.0f, 13.0f)
                    curveTo(0.0f, 14.0f, 1.0f, 14.0f, 1.0f, 14.0f)
                    horizontalLineTo(5.2161f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF241912)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(4.5f, 8.0f)
                    curveTo(5.1631f, 8.0f, 5.799f, 7.7366f, 6.2678f, 7.2678f)
                    curveTo(6.7366f, 6.799f, 7.0001f, 6.1631f, 7.0001f, 5.5f)
                    curveTo(7.0001f, 4.837f, 6.7366f, 4.2011f, 6.2678f, 3.7322f)
                    curveTo(5.799f, 3.2634f, 5.1631f, 3.0f, 4.5f, 3.0f)
                    curveTo(3.837f, 3.0f, 3.2011f, 3.2634f, 2.7322f, 3.7322f)
                    curveTo(2.2634f, 4.2011f, 2.0f, 4.837f, 2.0f, 5.5f)
                    curveTo(2.0f, 6.1631f, 2.2634f, 6.799f, 2.7322f, 7.2678f)
                    curveTo(3.2011f, 7.7366f, 3.837f, 8.0f, 4.5f, 8.0f)
                    close()
                }
            }
        }
            .build()
        return _icCategoryFollowerOpenLight!!
    }

private var _icCategoryFollowerOpenLight: ImageVector? = null

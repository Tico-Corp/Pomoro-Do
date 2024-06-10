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

val IconPack.IcFavoriteFilled: ImageVector
    get() {
        if (_icFavoriteFilled != null) {
            return _icFavoriteFilled!!
        }
        _icFavoriteFilled = Builder(
            name = "IcFavoriteFilled", defaultWidth = 15.0.dp, defaultHeight
            = 14.0.dp, viewportWidth = 15.0f, viewportHeight = 14.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFFF8DDB)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(7.7181f, 12.4542f)
                    lineTo(6.8723f, 11.6842f)
                    curveTo(3.8681f, 8.96f, 1.8848f, 7.1633f, 1.8848f, 4.9583f)
                    curveTo(1.8848f, 3.1617f, 3.2964f, 1.75f, 5.0931f, 1.75f)
                    curveTo(6.1081f, 1.75f, 7.0823f, 2.2225f, 7.7181f, 2.9692f)
                    curveTo(8.3539f, 2.2225f, 9.3281f, 1.75f, 10.3431f, 1.75f)
                    curveTo(12.1398f, 1.75f, 13.5514f, 3.1617f, 13.5514f, 4.9583f)
                    curveTo(13.5514f, 7.1633f, 11.5681f, 8.96f, 8.5639f, 11.69f)
                    lineTo(7.7181f, 12.4542f)
                    close()
                }
            }
        }
            .build()
        return _icFavoriteFilled!!
    }

private var _icFavoriteFilled: ImageVector? = null

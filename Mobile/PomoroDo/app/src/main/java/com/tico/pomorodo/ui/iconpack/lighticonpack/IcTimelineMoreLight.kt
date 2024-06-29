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

val LightIconPack.IcTimelineMoreLight: ImageVector
    get() {
        if (_icTimelineMoreLight != null) {
            return _icTimelineMoreLight!!
        }
        _icTimelineMoreLight = Builder(
            name = "IcTimelineMoreLight", defaultWidth = 18.0.dp,
            defaultHeight = 18.0.dp, viewportWidth = 18.0f, viewportHeight = 18.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF8A7362)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(9.0f, 6.0f)
                    curveTo(9.825f, 6.0f, 10.5f, 5.325f, 10.5f, 4.5f)
                    curveTo(10.5f, 3.675f, 9.825f, 3.0f, 9.0f, 3.0f)
                    curveTo(8.175f, 3.0f, 7.5f, 3.675f, 7.5f, 4.5f)
                    curveTo(7.5f, 5.325f, 8.175f, 6.0f, 9.0f, 6.0f)
                    close()
                    moveTo(9.0f, 7.5f)
                    curveTo(8.175f, 7.5f, 7.5f, 8.175f, 7.5f, 9.0f)
                    curveTo(7.5f, 9.825f, 8.175f, 10.5f, 9.0f, 10.5f)
                    curveTo(9.825f, 10.5f, 10.5f, 9.825f, 10.5f, 9.0f)
                    curveTo(10.5f, 8.175f, 9.825f, 7.5f, 9.0f, 7.5f)
                    close()
                    moveTo(9.0f, 12.0f)
                    curveTo(8.175f, 12.0f, 7.5f, 12.675f, 7.5f, 13.5f)
                    curveTo(7.5f, 14.325f, 8.175f, 15.0f, 9.0f, 15.0f)
                    curveTo(9.825f, 15.0f, 10.5f, 14.325f, 10.5f, 13.5f)
                    curveTo(10.5f, 12.675f, 9.825f, 12.0f, 9.0f, 12.0f)
                    close()
                }
            }
        }
            .build()
        return _icTimelineMoreLight!!
    }

private var _icTimelineMoreLight: ImageVector? = null

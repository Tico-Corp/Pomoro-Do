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

val IconPack.IcFavoriteOutline: ImageVector
    get() {
        if (_icFavoriteOutline != null) {
            return _icFavoriteOutline!!
        }
        _icFavoriteOutline = Builder(
            name = "IcFavoriteOutline", defaultWidth = 15.0.dp,
            defaultHeight = 15.0.dp, viewportWidth = 15.0f, viewportHeight = 15.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFF8DDB)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero
                ) {
                    moveTo(6.9303f, 12.149f)
                    lineTo(6.9296f, 12.1484f)
                    curveTo(5.3102f, 10.6799f, 4.0066f, 9.4966f, 3.1019f, 8.3905f)
                    curveTo(2.2029f, 7.2914f, 1.75f, 6.3295f, 1.75f, 5.3125f)
                    curveTo(1.75f, 3.6636f, 3.0386f, 2.375f, 4.6875f, 2.375f)
                    curveTo(5.6236f, 2.375f, 6.5299f, 2.8132f, 7.1193f, 3.5054f)
                    lineTo(7.5f, 3.9525f)
                    lineTo(7.8807f, 3.5054f)
                    curveTo(8.4701f, 2.8132f, 9.3764f, 2.375f, 10.3125f, 2.375f)
                    curveTo(11.9614f, 2.375f, 13.25f, 3.6636f, 13.25f, 5.3125f)
                    curveTo(13.25f, 6.3295f, 12.7971f, 7.2914f, 11.898f, 8.3914f)
                    curveTo(10.9933f, 9.4983f, 9.6899f, 10.6829f, 8.0706f, 12.1544f)
                    curveTo(8.0704f, 12.1546f, 8.0702f, 12.1548f, 8.07f, 12.155f)
                    lineTo(7.5013f, 12.6688f)
                    lineTo(6.9303f, 12.149f)
                    close()
                }
            }
        }
            .build()
        return _icFavoriteOutline!!
    }

private var _icFavoriteOutline: ImageVector? = null

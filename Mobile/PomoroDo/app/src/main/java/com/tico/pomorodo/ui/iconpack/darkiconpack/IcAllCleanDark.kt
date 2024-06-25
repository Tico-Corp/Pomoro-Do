package com.tico.pomorodo.ui.iconpack.darkiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.DarkIconPack

val DarkIconPack.IcAllCleanDark: ImageVector
    get() {
        if (_icAllCleanDark != null) {
            return _icAllCleanDark!!
        }
        _icAllCleanDark = Builder(
            name = "IcAllCleanDark", defaultWidth = 32.0.dp, defaultHeight =
            32.0.dp, viewportWidth = 32.0f, viewportHeight = 32.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFB4B4B4)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.0005f, 17.8666f)
                lineTo(12.1114f, 21.7666f)
                curveTo(11.8655f, 22.011f, 11.5526f, 22.1332f, 11.1726f, 22.1332f)
                curveTo(10.7926f, 22.1332f, 10.4797f, 22.011f, 10.2339f, 21.7666f)
                curveTo(9.9894f, 21.5221f, 9.8672f, 21.211f, 9.8672f, 20.8332f)
                curveTo(9.8672f, 20.4555f, 9.9894f, 20.1443f, 10.2339f, 19.8999f)
                lineTo(14.1339f, 15.9999f)
                lineTo(10.2339f, 12.1442f)
                curveTo(9.9894f, 11.8983f, 9.8672f, 11.5853f, 9.8672f, 11.2054f)
                curveTo(9.8672f, 10.8254f, 9.9894f, 10.5124f, 10.2339f, 10.2666f)
                curveTo(10.4783f, 10.0221f, 10.7894f, 9.8999f, 11.1672f, 9.8999f)
                curveTo(11.545f, 9.8999f, 11.8561f, 10.0221f, 12.1005f, 10.2666f)
                lineTo(16.0005f, 14.1666f)
                lineTo(19.8563f, 10.2666f)
                curveTo(20.1021f, 10.0221f, 20.4151f, 9.8999f, 20.7951f, 9.8999f)
                curveTo(21.1751f, 9.8999f, 21.488f, 10.0221f, 21.7339f, 10.2666f)
                curveTo(22.0005f, 10.5332f, 22.1339f, 10.8499f, 22.1339f, 11.2166f)
                curveTo(22.1339f, 11.5832f, 22.0005f, 11.8888f, 21.7339f, 12.1332f)
                lineTo(17.8339f, 15.9999f)
                lineTo(21.7339f, 19.889f)
                curveTo(21.9783f, 20.1349f, 22.1005f, 20.4478f, 22.1005f, 20.8278f)
                curveTo(22.1005f, 21.2078f, 21.9783f, 21.5207f, 21.7339f, 21.7666f)
                curveTo(21.4672f, 22.0332f, 21.1505f, 22.1666f, 20.7839f, 22.1666f)
                curveTo(20.4172f, 22.1666f, 20.1116f, 22.0332f, 19.8672f, 21.7666f)
                lineTo(16.0005f, 17.8666f)
                close()
            }
        }
            .build()
        return _icAllCleanDark!!
    }

private var _icAllCleanDark: ImageVector? = null

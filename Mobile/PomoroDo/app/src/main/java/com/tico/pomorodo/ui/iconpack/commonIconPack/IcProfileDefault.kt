package com.tico.pomorodo.ui.iconpack.commonIconPack

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

val IconPack.IcProfileDefault: ImageVector
    get() {
        if (_icProfileDefault != null) {
            return _icProfileDefault!!
        }
        _icProfileDefault = Builder(
            name = "IcProfileDefault", defaultWidth = 150.0.dp,
            defaultHeight = 150.0.dp, viewportWidth = 150.0f, viewportHeight = 150.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFD9D9D9)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(75.0f, 0.0f)
                    lineTo(75.0f, 0.0f)
                    arcTo(75.0f, 75.0f, 0.0f, false, true, 150.0f, 75.0f)
                    lineTo(150.0f, 75.0f)
                    arcTo(75.0f, 75.0f, 0.0f, false, true, 75.0f, 150.0f)
                    lineTo(75.0f, 150.0f)
                    arcTo(75.0f, 75.0f, 0.0f, false, true, 0.0f, 75.0f)
                    lineTo(0.0f, 75.0f)
                    arcTo(75.0f, 75.0f, 0.0f, false, true, 75.0f, 0.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFB4B4B4)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(75.0f, 168.0f)
                    moveToRelative(-75.0f, 0.0f)
                    arcToRelative(75.0f, 75.0f, 0.0f, true, true, 150.0f, 0.0f)
                    arcToRelative(75.0f, 75.0f, 0.0f, true, true, -150.0f, 0.0f)
                }
                path(
                    fill = SolidColor(Color(0xFFB4B4B4)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(75.0f, 57.0f)
                    moveToRelative(-32.0f, 0.0f)
                    arcToRelative(32.0f, 32.0f, 0.0f, true, true, 64.0f, 0.0f)
                    arcToRelative(32.0f, 32.0f, 0.0f, true, true, -64.0f, 0.0f)
                }
            }
        }
            .build()
        return _icProfileDefault!!
    }

private var _icProfileDefault: ImageVector? = null

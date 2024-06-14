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

val DarkIconPack.IcAddCategoryDark: ImageVector
    get() {
        if (_icAddCategoryDark != null) {
            return _icAddCategoryDark!!
        }
        _icAddCategoryDark = Builder(
            name = "IcAddCategoryDark", defaultWidth = 14.0.dp,
            defaultHeight = 14.0.dp, viewportWidth = 14.0f, viewportHeight = 14.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFF5F5F5)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(11.0827f, 7.5833f)
                    horizontalLineTo(7.5827f)
                    verticalLineTo(11.0833f)
                    horizontalLineTo(6.416f)
                    verticalLineTo(7.5833f)
                    horizontalLineTo(2.916f)
                    verticalLineTo(6.4166f)
                    horizontalLineTo(6.416f)
                    verticalLineTo(2.9166f)
                    horizontalLineTo(7.5827f)
                    verticalLineTo(6.4166f)
                    horizontalLineTo(11.0827f)
                    verticalLineTo(7.5833f)
                    close()
                }
            }
        }
            .build()
        return _icAddCategoryDark!!
    }

private var _icAddCategoryDark: ImageVector? = null

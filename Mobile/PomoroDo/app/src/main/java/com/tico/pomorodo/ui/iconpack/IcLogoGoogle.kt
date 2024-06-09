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

val IconPack.IcLogoGoogle: ImageVector
    get() {
        if (_icLogoGoogle != null) {
            return _icLogoGoogle!!
        }
        _icLogoGoogle = Builder(
            name = "IcLogoGoogle", defaultWidth = 20.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF4285F4)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(19.6f, 10.2271f)
                    curveTo(19.6f, 9.518f, 19.5364f, 8.8362f, 19.4182f, 8.1816f)
                    horizontalLineTo(10.0f)
                    verticalLineTo(12.0498f)
                    horizontalLineTo(15.3818f)
                    curveTo(15.15f, 13.2998f, 14.4455f, 14.3589f, 13.3864f, 15.068f)
                    verticalLineTo(17.5771f)
                    horizontalLineTo(16.6182f)
                    curveTo(18.5091f, 15.8362f, 19.6f, 13.2725f, 19.6f, 10.2271f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF34A853)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(9.9999f, 20.0004f)
                    curveTo(12.6999f, 20.0004f, 14.9635f, 19.1049f, 16.618f, 17.5777f)
                    lineTo(13.3862f, 15.0686f)
                    curveTo(12.4908f, 15.6686f, 11.3453f, 16.0231f, 9.9999f, 16.0231f)
                    curveTo(7.3953f, 16.0231f, 5.1908f, 14.264f, 4.4044f, 11.9004f)
                    horizontalLineTo(1.0635f)
                    verticalLineTo(14.4913f)
                    curveTo(2.709f, 17.7595f, 6.0908f, 20.0004f, 9.9999f, 20.0004f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFFBBC04)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(4.4045f, 11.8997f)
                    curveTo(4.2045f, 11.2997f, 4.0909f, 10.6588f, 4.0909f, 9.9997f)
                    curveTo(4.0909f, 9.3406f, 4.2045f, 8.6997f, 4.4045f, 8.0997f)
                    verticalLineTo(5.5088f)
                    horizontalLineTo(1.0636f)
                    curveTo(0.3864f, 6.8588f, 0.0f, 8.3861f, 0.0f, 9.9997f)
                    curveTo(0.0f, 11.6133f, 0.3864f, 13.1406f, 1.0636f, 14.4906f)
                    lineTo(4.4045f, 11.8997f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFE94235)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(9.9999f, 3.9773f)
                    curveTo(11.468f, 3.9773f, 12.7862f, 4.4818f, 13.8226f, 5.4727f)
                    lineTo(16.6908f, 2.6045f)
                    curveTo(14.959f, 0.9909f, 12.6953f, 0.0f, 9.9999f, 0.0f)
                    curveTo(6.0908f, 0.0f, 2.709f, 2.2409f, 1.0635f, 5.5091f)
                    lineTo(4.4044f, 8.1f)
                    curveTo(5.1908f, 5.7364f, 7.3953f, 3.9773f, 9.9999f, 3.9773f)
                    close()
                }
            }
        }
            .build()
        return _icLogoGoogle!!
    }

private var _icLogoGoogle: ImageVector? = null

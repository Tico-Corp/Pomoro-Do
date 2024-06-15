package com.tico.pomorodo.ui.iconpack.commonIconPack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.ui.theme.IconPack

val IconPack.IcAllCheckTodoState: ImageVector
    get() {
        if (_icAllCheckTodoState != null) {
            return _icAllCheckTodoState!!
        }
        _icAllCheckTodoState = Builder(
            name = "IcAllCheckTodoState", defaultWidth = 15.0.dp,
            defaultHeight = 14.0.dp, viewportWidth = 15.0f, viewportHeight = 14.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFC090)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.6016f, 12.25f)
                curveTo(10.5011f, 12.25f, 12.8516f, 9.8995f, 12.8516f, 7.0f)
                curveTo(12.8516f, 4.1005f, 10.5011f, 1.75f, 7.6016f, 1.75f)
                curveTo(4.7021f, 1.75f, 2.3516f, 4.1005f, 2.3516f, 7.0f)
                curveTo(2.3516f, 9.8995f, 4.7021f, 12.25f, 7.6016f, 12.25f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF241912)),
                strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(7.6016f, 12.25f)
                curveTo(8.8162f, 12.25f, 9.9932f, 11.8288f, 10.9321f, 11.0583f)
                curveTo(11.871f, 10.2878f, 12.5138f, 9.2155f, 12.7507f, 8.0242f)
                curveTo(12.9877f, 6.8329f, 12.8042f, 5.5964f, 12.2317f, 4.5252f)
                curveTo(11.6591f, 3.454f, 10.7328f, 2.6144f, 9.6107f, 2.1496f)
                curveTo(8.4885f, 1.6848f, 7.2399f, 1.6235f, 6.0776f, 1.9761f)
                curveTo(4.9153f, 2.3286f, 3.9112f, 3.0733f, 3.2364f, 4.0833f)
                curveTo(2.5615f, 5.0932f, 2.2578f, 6.3058f, 2.3768f, 7.5146f)
                curveTo(2.4959f, 8.7233f, 3.0304f, 9.8534f, 3.8893f, 10.7123f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF241912)),
                strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(9.9342f, 5.8335f)
                lineTo(7.8354f, 8.352f)
                curveTo(7.453f, 8.811f, 7.2618f, 9.0404f, 7.0048f, 9.0521f)
                curveTo(6.7478f, 9.0637f, 6.5366f, 8.8525f, 6.1142f, 8.4302f)
                lineTo(5.2676f, 7.5835f)
            }
        }
            .build()
        return _icAllCheckTodoState!!
    }

private var _icAllCheckTodoState: ImageVector? = null

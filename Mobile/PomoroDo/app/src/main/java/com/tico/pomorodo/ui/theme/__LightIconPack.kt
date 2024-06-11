package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomMyInfoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomTimerLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomTodoLight

object LightIconPack {
    private val _AllIcons by lazy {
        mapOf(
            IC_BOTTOM_MY_INFO to IcBottomMyInfoLight,
            IC_BOTTOM_TODO to IcBottomTodoLight,
            IC_BOTTOM_TIMER to IcBottomTimerLight,
        )
    }

    val AllIcons: Map<String, ImageVector>
        get() = _AllIcons
}
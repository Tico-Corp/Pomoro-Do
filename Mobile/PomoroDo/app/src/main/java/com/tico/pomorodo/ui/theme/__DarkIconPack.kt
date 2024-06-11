package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomMyInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomTimerDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomTodoDark

object DarkIconPack {
    private val _AllIcons by lazy {
        mapOf(
            IC_BOTTOM_MY_INFO to IcBottomMyInfoDark,
            IC_BOTTOM_TODO to IcBottomTodoDark,
            IC_BOTTOM_TIMER to IcBottomTimerDark,
        )
    }

    val AllIcons: Map<String, ImageVector>
        get() = _AllIcons
}
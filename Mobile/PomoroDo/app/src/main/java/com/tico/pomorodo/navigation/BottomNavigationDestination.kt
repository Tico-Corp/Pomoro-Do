package com.tico.pomorodo.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.iconpack.IcBottomMyInfo
import com.tico.pomorodo.ui.iconpack.IcBottomTimer
import com.tico.pomorodo.ui.iconpack.IcBottomTodo
import com.tico.pomorodo.ui.theme.IconPack

enum class BottomNavigationDestination(
    val iconVector: ImageVector,
    @StringRes val iconTextId: Int,
) {
    TIMER(
        iconVector = IconPack.IcBottomTimer,
        iconTextId = R.string.title_timer,
    ),
    TODO(
        iconVector = IconPack.IcBottomTodo,
        iconTextId = R.string.title_todo,
    ),
    MY_INFO(
        iconVector = IconPack.IcBottomMyInfo,
        iconTextId = R.string.title_my_info,
    )
}
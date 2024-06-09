package com.tico.pomorodo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tico.pomorodo.R

enum class BottomNavigationDestination(
    @DrawableRes val selectedIcon: Int,
    @StringRes val iconTextId: Int,
) {
    TIMER(
        selectedIcon = R.drawable.ic_bottom_timer,
        iconTextId = R.string.title_timer,
    ),
    TODO(
        selectedIcon = R.drawable.ic_bottom_todo,
        iconTextId = R.string.title_todo,
    ),
    MY_INFO(
        selectedIcon = R.drawable.ic_bottom_my_info,
        iconTextId = R.string.title_my_info,
    )
}
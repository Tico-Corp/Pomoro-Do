package com.tico.pomorodo.navigation

import androidx.annotation.StringRes
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.IC_BOTTOM_MY_INFO
import com.tico.pomorodo.ui.theme.IC_BOTTOM_TIMER
import com.tico.pomorodo.ui.theme.IC_BOTTOM_TODO

enum class BottomNavigationDestination(
    val iconString: String,
    @StringRes val iconTextId: Int,
) {
    TIMER(
        iconString = IC_BOTTOM_TIMER,
        iconTextId = R.string.title_timer,
    ),
    TODO(
        iconString = IC_BOTTOM_TODO,
        iconTextId = R.string.title_todo,
    ),
    MY_INFO(
        iconString = IC_BOTTOM_MY_INFO,
        iconTextId = R.string.title_my_info,
    )
}
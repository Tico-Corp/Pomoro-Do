package com.tico.pomorodo.navigation

import androidx.annotation.StringRes
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.IC_BOTTOM_FOLLOW
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
    FOLLOW(
        iconString = IC_BOTTOM_FOLLOW,
        iconTextId = R.string.title_follow,
    ),
    MY_INFO(
        iconString = IC_BOTTOM_MY_INFO,
        iconTextId = R.string.title_my_info,
    )
}

// 이곳에 디바이스 전체 화면을 차지하는 화면들을 넣어주세요.(하단 바 없는 화면들)
enum class MainNavigationDestination {
    SPLASH,

    LOG_IN,
    SIGN_UP,

    HOME,

    CONCENTRATION_MODE,
    BREAK_MODE,

    CATEGORY,
    ADD_CATEGORY,
    INFO_CATEGORY,
    GROUP_MEMBER_CHOOSE,

    HISTORY,

    MODIFY_PROFILE,
    FOLLOW,

    SETTING,
    APP_THEME,
}
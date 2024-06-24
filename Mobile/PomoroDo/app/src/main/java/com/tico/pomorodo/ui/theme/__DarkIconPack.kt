package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcAddCategoryDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcAddTodoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcAllCleanDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcArrowBackDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcArrowFrontDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcArrowRightDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomMyInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomTimerDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomTodoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateGreenDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateOrangeDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateRedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateWhiteDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateYellowDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDropDownDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCategoryFollowerOpenDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCategoryFullOpenDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCategoryLockDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcDropDownDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcDropDownDisableDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcGroupOpenDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcGroupSelectedCancleDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcGroupSelectedUncheckedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcMoreInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcOkDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoCheckedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoGoingDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoMoreInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoUncheckedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcUnokDark

object DarkIconPack {
    private val _AllIcons by lazy {
        mapOf(
            IC_BOTTOM_MY_INFO to IcBottomMyInfoDark,
            IC_BOTTOM_TODO to IcBottomTodoDark,
            IC_BOTTOM_TIMER to IcBottomTimerDark,
            IC_ARROW_FRONT to IcArrowFrontDark,
            IC_ARROW_BACK to IcArrowBackDark,
            IC_CALENDAR_DROP_DOWN to IcCalendarDropDownDark,
            IC_ADD_TODO to IcAddTodoDark,
            IC_TODO_UNCHECKED to IcTodoUncheckedDark,
            IC_TODO_CHECKED to IcTodoCheckedDark,
            IC_TODO_GOING to IcTodoGoingDark,
            IC_TODO_MORE_INFO to IcTodoMoreInfoDark,
            IC_MORE_INFO to IcMoreInfoDark,
            IC_ADD_CATEGORY to IcAddCategoryDark,
            IC_CALENDAR_DATE_WHITE to IcCalendarDateWhiteDark,
            IC_CALENDAR_DATE_GREEN to IcCalendarDateGreenDark,
            IC_CALENDAR_DATE_YELLOW to IcCalendarDateYellowDark,
            IC_CALENDAR_DATE_ORANGE to IcCalendarDateOrangeDark,
            IC_CALENDAR_DATE_RED to IcCalendarDateRedDark,
            IC_OK to IcOkDark,
            IC_CATEGORY_FULL_OPEN to IcCategoryFullOpenDark,
            IC_DROP_DOWN to IcDropDownDark,
            IC_DROP_DOWN_DISABLE to IcDropDownDisableDark,
            IC_UNOK to IcUnokDark,
            IC_CATEGORY_FOLLOWER_OPEN to IcCategoryFollowerOpenDark,
            IC_CATEGORY_GROUP_OPEN to IcGroupOpenDark,
            IC_CATEGORY_ONLY_ME_OPEN to IcCategoryLockDark,
            IC_SELECTED_GROUP_MEMBER_CANCEL to IcGroupSelectedCancleDark,
            IC_GROUP_SELECTED_UNCHECKED to IcGroupSelectedUncheckedDark,
            IC_ARROW_RIGHT to IcArrowRightDark,
            IC_ALL_CLEAN to IcAllCleanDark,
        )
    }

    val AllIcons: Map<String, ImageVector>
        get() = _AllIcons
}
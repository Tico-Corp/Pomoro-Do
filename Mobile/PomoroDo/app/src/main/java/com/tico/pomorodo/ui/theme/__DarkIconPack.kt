package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcAddCategoryDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcAddTodoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcArrowBackDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcArrowFrontDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomMyInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomTimerDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomTodoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateRedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateWhiteDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDropDownDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcMoreInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoCheckedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoGoingDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoUncheckedDark

object DarkIconPack {
    private val _AllIcons by lazy {
        mapOf(
            IC_BOTTOM_MY_INFO to IcBottomMyInfoDark,
            IC_BOTTOM_TODO to IcBottomTodoDark,
            IC_BOTTOM_TIMER to IcBottomTimerDark,
            IC_ARROW_FRONT to IcArrowFrontDark,
            IC_ARROW_BACK to IcArrowBackDark,
            IC_CALENDAR_DATE_RED to IcCalendarDateRedDark,
            IC_CALENDAR_DROP_DOWN to IcCalendarDropDownDark,
            IC_CALENDAR_DATE_WHITE to IcCalendarDateWhiteDark,
            IC_ADD_TODO to IcAddTodoDark,
            IC_TODO_UNCHECKED to IcTodoUncheckedDark,
            IC_TODO_CHECKED to IcTodoCheckedDark,
            IC_TODO_GOING to IcTodoGoingDark,
            IC_MORE_INFO to IcMoreInfoDark,
            IC_ADD_CATEGORY to IcAddCategoryDark,
        )
    }

    val AllIcons: Map<String, ImageVector>
        get() = _AllIcons
}
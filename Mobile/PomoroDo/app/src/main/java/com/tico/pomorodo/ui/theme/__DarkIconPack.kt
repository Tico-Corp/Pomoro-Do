package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomMyInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcBottomTodoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateGreenDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateOrangeDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateRedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateWhiteDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDateYellowDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcCalendarDropDownDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcMoreInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoCheckedDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoGoingDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoMoreInfoDark
import com.tico.pomorodo.ui.iconpack.darkiconpack.IcTodoUncheckedDark

object DarkIconPack {
    private val _AllIcons by lazy {
        mapOf(
            IC_BOTTOM_MY_INFO to IcBottomMyInfoDark,
            IC_BOTTOM_TODO to IcBottomTodoDark,
            BG_CIRCULAR_TIMER to BgCircularTimerDark,
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
        )
    }

    val AllIcons: Map<String, ImageVector>
        get() = _AllIcons
}
package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomMyInfoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomTimerLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomTodoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateGreenLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateOrangeLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateRedLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateWhiteLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateYellowLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDropDownLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcMoreInfoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoCheckedLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoGoingLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoMoreInfoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoUncheckedLight

object LightIconPack {
    private val _AllIcons by lazy {
        mapOf(
            IC_BOTTOM_MY_INFO to IcBottomMyInfoLight,
            IC_BOTTOM_TODO to IcBottomTodoLight,
            IC_BOTTOM_TIMER to IcBottomTimerLight,
            BG_CIRCULAR_TIMER to BgCircularTimerLight,
            IC_ARROW_FRONT to IcArrowFrontLight,
            IC_ARROW_BACK to IcArrowBackLight,
            IC_CALENDAR_DROP_DOWN to IcCalendarDropDownLight,
            IC_ADD_TODO to IcAddTodoLight,
            IC_TODO_UNCHECKED to IcTodoUncheckedLight,
            IC_TODO_CHECKED to IcTodoCheckedLight,
            IC_TODO_GOING to IcTodoGoingLight,
            IC_TODO_MORE_INFO to IcTodoMoreInfoLight,
            IC_MORE_INFO to IcMoreInfoLight,
            IC_ADD_CATEGORY to IcAddCategoryLight,
            IC_CALENDAR_DATE_WHITE to IcCalendarDateWhiteLight,
            IC_CALENDAR_DATE_GREEN to IcCalendarDateGreenLight,
            IC_CALENDAR_DATE_YELLOW to IcCalendarDateYellowLight,
            IC_CALENDAR_DATE_ORANGE to IcCalendarDateOrangeLight,
            IC_CALENDAR_DATE_RED to IcCalendarDateRedLight,
        )
    }

    val AllIcons: Map<String, ImageVector>
        get() = _AllIcons
}
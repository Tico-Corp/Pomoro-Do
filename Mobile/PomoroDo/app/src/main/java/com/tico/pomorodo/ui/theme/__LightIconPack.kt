package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.lighticonpack.BgCircularTimerLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcAddCategoryLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcAddTodoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcAllCleanLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcArrowBackLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcArrowFrontLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcArrowRightLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomFollowLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomMyInfoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomTimerLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcBottomTodoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateGreenLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateOrangeLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateRedLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateWhiteLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDateYellowLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCalendarDropDownLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCategoryFollowerOpenLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCategoryFullOpenLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcCategoryLockLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcDropDownDisableLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcDropDownLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcGroupOpenLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcGroupSelectedCancleLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcGroupSelectedUncheckedLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcMoreInfoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcMuteLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcOkLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcSearchLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcSoundLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTimelineMoreLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoCheckedLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoGoingLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoMoreInfoLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcTodoUncheckedLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcUnokLight
import com.tico.pomorodo.ui.iconpack.lighticonpack.IcVibrateLight

object LightIconPack {
    private val _AllIcons by lazy {
        mapOf(
            IC_BOTTOM_MY_INFO to IcBottomMyInfoLight,
            IC_BOTTOM_TODO to IcBottomTodoLight,
            IC_BOTTOM_FOLLOW to IcBottomFollowLight,
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
            IC_OK to IcOkLight,
            IC_CATEGORY_FULL_OPEN to IcCategoryFullOpenLight,
            IC_DROP_DOWN to IcDropDownLight,
            IC_DROP_DOWN_DISABLE to IcDropDownDisableLight,
            IC_UNOK to IcUnokLight,
            IC_CATEGORY_FOLLOWER_OPEN to IcCategoryFollowerOpenLight,
            IC_CATEGORY_GROUP_OPEN to IcGroupOpenLight,
            IC_CATEGORY_ONLY_ME_OPEN to IcCategoryLockLight,
            IC_SELECTED_GROUP_MEMBER_CANCEL to IcGroupSelectedCancleLight,
            IC_GROUP_SELECTED_UNCHECKED to IcGroupSelectedUncheckedLight,
            IC_ARROW_RIGHT to IcArrowRightLight,
            IC_ALL_CLEAN to IcAllCleanLight,
            IC_TIMELINE_MORE_INFO to IcTimelineMoreLight,
            IC_ALARM_OPTION_SOUND to IcSoundLight,
            IC_ALARM_OPTION_VIBRATE to IcVibrateLight,
            IC_ALARM_OPTION_MUTE to IcMuteLight,
            IC_SEARCH to IcSearchLight,
        )
    }

    val AllIcons: Map<String, ImageVector>
        get() = _AllIcons
}
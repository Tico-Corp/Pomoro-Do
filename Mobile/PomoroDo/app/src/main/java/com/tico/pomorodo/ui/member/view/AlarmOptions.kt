package com.tico.pomorodo.ui.member.view

import androidx.annotation.StringRes
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.IC_ALARM_OPTION_MUTE
import com.tico.pomorodo.ui.theme.IC_ALARM_OPTION_SOUND
import com.tico.pomorodo.ui.theme.IC_ALARM_OPTION_VIBRATE

enum class AlarmOptions(@StringRes val title: Int, val iconName: String) {
    Sound(R.string.content_alarm_option_sound, IC_ALARM_OPTION_SOUND),
    Vibrate(R.string.content_alarm_option_vibrate, IC_ALARM_OPTION_VIBRATE),
    Mute(R.string.content_alarm_option_mute, IC_ALARM_OPTION_MUTE)
}
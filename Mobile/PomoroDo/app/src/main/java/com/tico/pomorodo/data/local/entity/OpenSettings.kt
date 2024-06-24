package com.tico.pomorodo.data.local.entity

import androidx.annotation.StringRes
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.IC_CATEGORY_FOLLOWER_OPEN
import com.tico.pomorodo.ui.theme.IC_CATEGORY_FULL_OPEN
import com.tico.pomorodo.ui.theme.IC_CATEGORY_GROUP_OPEN
import com.tico.pomorodo.ui.theme.IC_CATEGORY_ONLY_ME_OPEN


enum class OpenSettings(
    val iconString: String,
    @StringRes val descriptionId: Int,
    @StringRes val textId: Int,
    val enabled: Boolean
) {
    FULL(IC_CATEGORY_FULL_OPEN, R.string.content_full_open, R.string.content_full_open, true),
    FOLLOWER(
        IC_CATEGORY_FOLLOWER_OPEN,
        R.string.content_follower_open,
        R.string.content_follower_open,
        true
    ),
    ME(
        IC_CATEGORY_ONLY_ME_OPEN,
        R.string.content_only_me_open,
        R.string.content_only_me_open,
        true
    ),
    GROUP(IC_CATEGORY_GROUP_OPEN, R.string.content_group_open, R.string.content_group_open, false)
}
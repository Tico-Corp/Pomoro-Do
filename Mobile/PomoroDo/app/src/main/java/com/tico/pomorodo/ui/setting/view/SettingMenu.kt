package com.tico.pomorodo.ui.setting.view

import androidx.annotation.StringRes
import com.tico.pomorodo.R


const val LIGHT = "light"
const val DARK = "dark"
const val SYSTEM = "system"

val appThemeMode = mapOf(
    LIGHT to R.string.content_app_theme_light,
    DARK to (R.string.content_app_theme_dark),
    SYSTEM to (R.string.content_app_theme_system)
)

enum class SettingMenu(
    @StringRes val titleResId: Int,
    val type: SettingMenuType,
    var content: String?,
    val onClick: () -> Unit
) {
    MODIFY_PROFILE(
        titleResId = R.string.title_modify_profile,
        type = SettingMenuType.OTHERS,
        content = null,
        onClick = {}),
    APP_THEME(
        titleResId = R.string.title_app_theme,
        type = SettingMenuType.APP_THEME,
        content = LIGHT,
        onClick = {}),
    TERMS_OF_USE(
        titleResId = R.string.title_terms_of_use,
        type = SettingMenuType.OTHERS,
        content = null,
        onClick = {}),
    PRIVACY_POLICY(
        titleResId = R.string.title_privacy_policy,
        type = SettingMenuType.OTHERS,
        content = null,
        onClick = {}),
    APP_VERSION(
        titleResId = R.string.title_app_version,
        type = SettingMenuType.APP_VERSION,
        content = "1.0.0",
        onClick = {});

    fun SettingMenu.setAppTheme(mode: String): Boolean =
        if (this.type == SettingMenuType.APP_THEME) {
            this.content = mode
            true
        } else {
            false
        }
}

enum class SettingMenuType {
    APP_THEME, APP_VERSION, OTHERS
}
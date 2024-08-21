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
) {
    ModifyProfile(
        titleResId = R.string.title_modify_profile,
        type = SettingMenuType.Others,
        content = null,
    ),
    AppTheme(
        titleResId = R.string.title_app_theme,
        type = SettingMenuType.AppTheme,
        content = LIGHT,
    ),
    TermsOfUse(
        titleResId = R.string.title_terms_of_use,
        type = SettingMenuType.Others,
        content = null,
    ),
    PrivacyPolicy(
        titleResId = R.string.title_privacy_policy,
        type = SettingMenuType.Others,
        content = null,
    ),
    AppVersion(
        titleResId = R.string.title_app_version,
        type = SettingMenuType.AppVersion,
        content = "1.0.0",
    );

    fun SettingMenu.setAppTheme(mode: String): Boolean =
        if (this.type == SettingMenuType.AppTheme) {
            this.content = mode
            true
        } else {
            false
        }
}

enum class SettingMenuType {
    AppTheme, AppVersion, Others
}
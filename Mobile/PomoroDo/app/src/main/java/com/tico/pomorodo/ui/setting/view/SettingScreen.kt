package com.tico.pomorodo.ui.setting.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTopAppBarWithSingleButton
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.clickableWithRipple
import com.tico.pomorodo.ui.theme.IC_ARROW_RIGHT
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun SettingScreen(
    navigateToModifyProfileScreen: () -> Unit,
    navigateToAppThemeScreen: () -> Unit,
    navigateToTermsOfUseScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit
) {
    PomoroDoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = PomoroDoTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomTopAppBarWithSingleButton(
                    title = stringResource(R.string.title_setting_screen),
                    navigationAction = { TODO("top app bar - pop back stack") }
                )

                SettingMenuList(
                    navigateToModifyProfileScreen,
                    navigateToAppThemeScreen,
                    navigateToTermsOfUseScreen,
                    navigateToPrivacyPolicyScreen,
                )
            }
        }
    }
}

@Composable
fun SettingMenuList(
    navigateToModifyProfileScreen: () -> Unit,
    navigateToAppThemeScreen: () -> Unit,
    navigateToTermsOfUseScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit
) {
    val menuList: Map<SettingMenu, (() -> Unit)?> = mapOf(
        SettingMenu.MODIFY_PROFILE to navigateToModifyProfileScreen,
        SettingMenu.APP_THEME to navigateToAppThemeScreen,
        SettingMenu.TERMS_OF_USE to navigateToTermsOfUseScreen,
        SettingMenu.PRIVACY_POLICY to navigateToPrivacyPolicyScreen,
        SettingMenu.APP_VERSION to null
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        menuList.entries.forEachIndexed { index, settingMenu ->
            SettingMenuItem(
                menuStringResId = settingMenu.key.titleResId,
                menuType = settingMenu.key.type,
                content = settingMenu.key.content,
                onClick = settingMenu.value
            )

            if (index != menuList.size - 1)
                HorizontalDivider(color = PomoroDoTheme.colorScheme.gray70)
        }
    }
}

@Composable
fun SettingMenuItem(
    @StringRes menuStringResId: Int,
    menuType: SettingMenuType,
    content: Any? = null,
    onClick: (() -> Unit)? = null
) {
    val modifier =
        if (onClick != null) Modifier.clickableWithRipple { onClick() } else Modifier

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(menuStringResId),
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular18
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            val contentString: String =
                if (menuType == SettingMenuType.APP_THEME)
                    stringResource(id = requireNotNull(appThemeMode[content]))
                else content?.toString() ?: ""

            Text(
                text = contentString,
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                style = PomoroDoTheme.typography.laundryGothicRegular14
            )

            Spacer(modifier = Modifier.width(2.dp))

            if (menuType != SettingMenuType.APP_VERSION)
                SimpleIcon(
                    size = 20,
                    imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_ARROW_RIGHT]),
                    contentDescriptionId = menuStringResId
                )
        }
    }
}
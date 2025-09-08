package com.tico.pomorodo.ui.setting.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTopAppBar
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.clickableWithRipple
import com.tico.pomorodo.ui.theme.IC_ARROW_RIGHT
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun SettingScreen(
    navigateToModifyProfileScreen: () -> Unit,
    navigateToAppThemeScreen: (String) -> Unit,
    navigateToTermsOfUseScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    popBackStack: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PomoroDoTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTopAppBar(
                titleTextId = R.string.title_setting_screen,
                onBackClickedListener = popBackStack
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

@Composable
fun SettingMenuList(
    navigateToModifyProfileScreen: () -> Unit,
    navigateToAppThemeScreen: (String) -> Unit,
    navigateToTermsOfUseScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 24.dp)
    ) {
        SettingMenuItem(menuStringResId = R.string.title_account_management) {
            navigateToModifyProfileScreen()
        }

        if (BuildConfig.APP_VERSION >= "2.0.0")
            SettingMenuItem(menuStringResId = R.string.title_app_theme) {
                /*TODO: 앱 테마 변경 화면 연결*/
                navigateToAppThemeScreen("")
            }

        SettingMenuItem(menuStringResId = R.string.title_terms_of_use) {
            navigateToTermsOfUseScreen()
        }

        SettingMenuItem(menuStringResId = R.string.title_privacy_policy) {
            navigateToPrivacyPolicyScreen()
        }

        /*TODO: 앱 버전 채우기*/
        SettingMenuItem(menuStringResId = R.string.title_app_version, appVersion = "")
    }
}

@Composable
fun SettingMenuItem(
    @StringRes menuStringResId: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickableWithRipple { onClick() }
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

        SimpleIcon(
            size = 20,
            imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_ARROW_RIGHT]),
            contentDescriptionId = menuStringResId
        )
    }
}

@Composable
fun SettingMenuItem(
    @StringRes menuStringResId: Int,
    appVersion: String,
) {
    Row(
        modifier = Modifier
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

        Text(
            text = appVersion,
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular14
        )

    }
}
package com.tico.pomorodo.ui.setting.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTopAppBarWithSingleButton
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.theme.IC_ARROW_RIGHT
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomTopAppBarWithSingleButton(
            title = "환경설정",
            navigationAction = { /*TODO: top app bar - pop back stack*/ }
        )

        SettingMenuList()
    }
}

@Composable
fun SettingMenuList() {
    SettingMenuItem(menu = "계정관리")
}

@Composable
fun SettingMenuItem(menu: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = menu,
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular18
        )

//        Icon(imageVector = , contentDescription = )
        SimpleIcon(
            size = 30,
            imageVector = PomoroDoTheme.iconPack[IC_ARROW_RIGHT]!!,
            contentDescriptionId = R.string.content_full_open
        )
    }
}
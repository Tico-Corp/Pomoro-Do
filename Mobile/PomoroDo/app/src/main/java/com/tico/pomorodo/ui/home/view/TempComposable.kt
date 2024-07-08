package com.tico.pomorodo.ui.home.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun MyInfoScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.title_my_info),
            style = PomoroDoTheme.typography.laundryGothicBold48
        )
    }
}
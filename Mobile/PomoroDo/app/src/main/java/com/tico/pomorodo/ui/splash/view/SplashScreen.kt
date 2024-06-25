package com.tico.pomorodo.ui.splash.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcTitle
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
internal fun SplashScreen(
    modifier: Modifier = Modifier
) {
    PomoroDoTheme {
        Surface(color = PomoroDoTheme.colorScheme.surface) {
            Icon(
                imageVector = IconPack.IcTitle,
                contentDescription = stringResource(R.string.content_ic_title),
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                tint = Color.Unspecified
            )
        }
    }
}
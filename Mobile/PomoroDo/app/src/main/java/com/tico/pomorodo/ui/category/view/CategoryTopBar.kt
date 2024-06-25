package com.tico.pomorodo.ui.category.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.IC_ARROW_BACK
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryTopBar(
    modifier: Modifier = Modifier,
    @StringRes titleTextId: Int,
    iconString: String,
    disableIconString: String? = null,
    @StringRes descriptionId: Int,
    onClickedListener: () -> Unit,
    onBackClickedListener: () -> Unit,
    enabled: Boolean = true
) {
    val topBarColors = TopAppBarColors(
        containerColor = PomoroDoTheme.colorScheme.background,
        titleContentColor = PomoroDoTheme.colorScheme.onBackground,
        scrolledContainerColor = PomoroDoTheme.colorScheme.primaryContainer,
        actionIconContentColor = PomoroDoTheme.colorScheme.primaryContainer,
        navigationIconContentColor = Color.Unspecified,
    )
    TopAppBar(
        modifier = modifier,
        title = {
            SimpleText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = titleTextId),
                textAlign = TextAlign.Center,
                style = PomoroDoTheme.typography.laundryGothicBold20,
                color = PomoroDoTheme.colorScheme.onBackground,
            )
        },
        colors = topBarColors,
        actions = {
            SimpleIconButton(
                modifier = Modifier.padding(18.dp),
                size = 28,
                imageVector = if (enabled) PomoroDoTheme.iconPack[iconString]!! else PomoroDoTheme.iconPack[disableIconString]!!,
                contentDescriptionId = descriptionId,
                enabled = enabled,
                onClickedListener = onClickedListener
            )
        },
        navigationIcon = {
            SimpleIconButton(
                modifier = Modifier.padding(18.dp),
                size = 28,
                imageVector = PomoroDoTheme.iconPack[IC_ARROW_BACK]!!,
                contentDescriptionId = R.string.content_ic_arrow_back,
                enabled = true,
                onClickedListener = onBackClickedListener
            )
        }
    )
}
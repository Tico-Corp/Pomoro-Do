package com.tico.pomorodo.ui.common.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.IC_ARROW_BACK
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleTextId: Int? = null,
    actionIconString: String? = null,
    actionDisableIconString: String? = null,
    @StringRes actionIconDescriptionId: Int? = null,
    onActionClickedListener: () -> Unit = {},
    onBackClickedListener: () -> Unit = {},
    isActionEnabled: Boolean = true,
    isBackIcon: Boolean = true,
    top: Int = 24,
    bottom: Int = 14,
    start: Int = 18,
    end: Int = 18
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = PomoroDoTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (isBackIcon) {
                SimpleIconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = top.dp, bottom = bottom.dp, start = start.dp, end = end.dp),
                    size = 28,
                    imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_ARROW_BACK]),
                    contentDescriptionId = R.string.content_ic_arrow_back,
                    enabled = true,
                    onClickedListener = onBackClickedListener
                )
            }
            titleTextId?.let {
                SimpleText(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = top.dp, bottom = bottom.dp),
                    text = stringResource(id = it),
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicBold20,
                    color = PomoroDoTheme.colorScheme.onBackground,
                )
            }
            if (actionIconString != null && actionIconDescriptionId != null) {
                SimpleIconButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = top.dp, bottom = bottom.dp, start = start.dp, end = end.dp),
                    size = 28,
                    imageVector = if (isActionEnabled) requireNotNull(PomoroDoTheme.iconPack[actionIconString])
                    else requireNotNull(PomoroDoTheme.iconPack[actionDisableIconString]),
                    contentDescriptionId = actionIconDescriptionId,
                    enabled = isActionEnabled,
                    onClickedListener = onActionClickedListener
                )
            }
        }
    }
}
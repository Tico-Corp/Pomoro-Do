package com.tico.pomorodo.ui.category.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.SimpleAlertDialog
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun EndOfWritingDialog(onConfirmation: () -> Unit, onDismissRequest: () -> Unit) {
    SimpleAlertDialog(
        dialogTitleId = R.string.title_end_of_writing,
        confirmTextId = R.string.content_finish,
        dismissTextId = R.string.content_cancel,
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest
    ) {
        SimpleText(
            textId = R.string.content_end_of_writing_message,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}
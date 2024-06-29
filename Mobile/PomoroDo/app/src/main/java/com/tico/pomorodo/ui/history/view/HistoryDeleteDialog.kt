package com.tico.pomorodo.ui.history.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.SimpleAlertDialog
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun HistoryDeleteDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: (Boolean) -> Unit,
) {
    SimpleAlertDialog(
        dialogTitleId = R.string.content_history_delete,
        confirmTextId = R.string.content_delete,
        dismissTextId = R.string.content_cancel,
        content = {
            SimpleText(
                textId = R.string.content_history_delete_message,
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        },
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest
    )
}
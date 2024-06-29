package com.tico.pomorodo.ui.common.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun SimpleDropDownMoreInfo(
    showMoreInfo: Boolean,
    onShowMoreInfoChange: (Boolean) -> Unit,
    @StringRes
    editTextId: Int,
    @StringRes
    deleteTextId: Int,
    onMoreInfoEditClicked: () -> Unit,
    onMoreInfoDeleteClicked: () -> Unit,
    paddingValues: PaddingValues
) {
    DropdownMenuNoPaddingVertical(
        expanded = showMoreInfo,
        onDismissRequest = { onShowMoreInfoChange(false) },
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
    ) {
        CustomDropdownMenuItem(
            textId = editTextId,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular10,
            onClick = {
                onMoreInfoEditClicked()
                onShowMoreInfoChange(false)
            },
            contentPadding = paddingValues
        )
        HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
        CustomDropdownMenuItem(
            textId = deleteTextId,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular10,
            onClick = {
                onMoreInfoDeleteClicked()
                onShowMoreInfoChange(false)
            },
            contentPadding = paddingValues
        )
    }
}
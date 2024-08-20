package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextField
import com.tico.pomorodo.ui.common.view.SimpleAlertDialog
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.getNoSpace
import com.tico.pomorodo.ui.theme.IC_ALL_CLEAN
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun GroupDeleteFirstDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    SimpleAlertDialog(
        dialogTitleId = R.string.title_group_delete,
        confirmTextId = R.string.content_next,
        dismissTextId = R.string.content_cancel,
        content = {
            SimpleText(
                textId = R.string.content_group_delete_first_message,
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        },
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun GroupDeleteSecondDialog(
    groupName: String,
    enabled: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        unfocusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        disabledTextColor = PomoroDoTheme.colorScheme.dialogGray20,
        errorTextColor = PomoroDoTheme.colorScheme.error50,
        focusedContainerColor = PomoroDoTheme.colorScheme.dialogGray90,
        unfocusedContainerColor = PomoroDoTheme.colorScheme.dialogGray90,
        cursorColor = PomoroDoTheme.colorScheme.primaryContainer,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )
    SimpleAlertDialog(
        dialogTitleId = R.string.title_group_delete,
        confirmTextId = R.string.content_delete,
        dismissTextId = R.string.content_cancel,
        content = {
            SimpleText(
                text = stringResource(
                    id = R.string.content_group_delete_second_message,
                    groupName.getNoSpace()
                ),
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    SimpleText(
                        text = stringResource(id = R.string.content_search_group_member),
                        style = PomoroDoTheme.typography.laundryGothicRegular14,
                        color = PomoroDoTheme.colorScheme.dialogGray50
                    )
                },
                colors = textFieldColors,
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp
                ),
                shape = RoundedCornerShape(5.dp),
                trailingIcon = {
                    if (value.isNotBlank()) {
                        SimpleIconButton(
                            size = 34,
                            imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_ALL_CLEAN]),
                            contentDescriptionId = R.string.content_ic_all_clean,
                            enabled = true,
                            onClickedListener = { onValueChange("") }
                        )
                    }
                },
                textStyle = PomoroDoTheme.typography.laundryGothicRegular14
            )
        },
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest,
        enabled = enabled
    )
}
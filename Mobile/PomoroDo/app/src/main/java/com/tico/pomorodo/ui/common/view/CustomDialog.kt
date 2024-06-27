package com.tico.pomorodo.ui.common.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun SimpleAlertDialog(
    @StringRes
    dialogTitleId: Int,
    @StringRes
    confirmTextId: Int,
    @StringRes
    dismissTextId: Int,
    enabled: Boolean = true,
    onConfirmation: () -> Unit,
    onDismissRequest: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = CardDefaults.cardColors(containerColor = PomoroDoTheme.colorScheme.dialogSurface)
    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.padding(horizontal = 40.dp),
            shape = RoundedCornerShape(15.dp),
            colors = colors,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SimpleText(
                    textId = dialogTitleId,
                    style = PomoroDoTheme.typography.laundryGothicBold20,
                    color = PomoroDoTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(14.dp))
                content()
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimpleNarrowTextButton(
                        textId = dismissTextId,
                        containColor = Color.Unspecified,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        textColor = PomoroDoTheme.colorScheme.onBackground,
                        onClicked = { onDismissRequest(false) },
                        paddingValues = PaddingValues(vertical = 8.dp, horizontal = 20.dp)
                    )
                    SimpleNarrowTextButton(
                        modifier = Modifier.wrapContentWidth(),
                        containColor = PomoroDoTheme.colorScheme.primaryContainer,
                        textId = confirmTextId,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        textColor = Color.White,
                        onClicked = onConfirmation,
                        enabled = enabled,
                        disabledContainerColor = PomoroDoTheme.colorScheme.dialogGray70,
                        paddingValues = PaddingValues(vertical = 8.dp, horizontal = 20.dp)
                    )
                }
            }
        }
    }
}


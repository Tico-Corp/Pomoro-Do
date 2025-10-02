package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun CategoryDeleteOptionDialog(
    title: String,
    content: String,
    onAllDeleteClicked: () -> Unit,
    onIncompletedTodoDeleteClicked: () -> Unit,
    onNoDeleteClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val colors = CardDefaults.cardColors(containerColor = PomoroDoTheme.colorScheme.dialogSurface)
    Dialog(
        onDismissRequest = onDismissRequest,
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
                    text = title,
                    style = PomoroDoTheme.typography.laundryGothicBold20,
                    color = PomoroDoTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(14.dp))
                SimpleText(
                    modifier = Modifier,
                    text = content,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    CustomTextButton(
                        text = stringResource(id = R.string.content_all_delete),
                        containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                        contentColor = Color.White,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        verticalPadding = 8.dp,
                        onClick = onAllDeleteClicked
                    )
                    CustomTextButton(
                        text = stringResource(id = R.string.content_incompleted_todo_delete),
                        containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                        contentColor = Color.White,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        verticalPadding = 8.dp,
                        onClick = onIncompletedTodoDeleteClicked
                    )
                    CustomTextButton(
                        text = stringResource(id = R.string.content_no_delete),
                        containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                        contentColor = Color.White,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        verticalPadding = 8.dp,
                        onClick = onNoDeleteClicked
                    )
                    CustomTextButton(
                        text = stringResource(id = R.string.content_cancel),
                        containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                        contentColor = Color.White,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        verticalPadding = 8.dp,
                        onClick = onDismissRequest
                    )
                }
            }
        }
    }
}
package com.tico.pomorodo.ui.common.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tico.pomorodo.R
import com.tico.pomorodo.data.local.datasource.DataSource.todoList
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
import com.tico.pomorodo.ui.theme.PomoroDoTheme

/**
 * Displays a dialog showing a list of to-do items.
 *
 * This dialog can be used in two scenarios:
 * 1. After completing a concentration session, it shows today's remaining to-do items for the user to check off.
 * 2. When editing the timeline, it shows all to-do items, including completed ones, for the user to update.
 *
 * The dialog includes:
 * - A title.
 * - A list of to-do items.
 * - A dismiss button that resets any changes made.
 * - A confirm button that applies the changes.
 *
 * Example usage:
 *
 * @sample PreviewDialog
 *
 * @param title The title of the dialog.
 * @param todoList The list of to-do items to display in the dialog.
 * @param confirmTextId The resource ID for the text on the confirm button.
 * @param confirmEnable Whether the confirm button is enabled.
 * @param onConfirmation Function to run when the confirm button is clicked.
 * @param onDismissRequest Function to run when the dismiss button is clicked.
 * @param onTodoStateChanged Function to run when the state of a to-do item is changed.
 */
@Composable
fun TodoListDialog(
    title: String,
    todoList: List<TodoData>,
    @StringRes confirmTextId: Int,
    confirmEnable: Boolean = true,
    onConfirmation: () -> Unit,
    onDismissRequest: (Boolean) -> Unit,
    onTodoStateChanged: (index: Int, todoState: TodoState) -> Unit
) {
    val colors = CardDefaults.cardColors(containerColor = PomoroDoTheme.colorScheme.dialogSurface)
    val textColor = PomoroDoTheme.colorScheme.onBackground

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
                    .padding(horizontal = 18.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicBold20
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.content_todo_list_dialog),
                    color = textColor,
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicRegular14
                )

                Spacer(modifier = Modifier.height(16.dp))

                TodoList(todoList = todoList, onStateChanged = onTodoStateChanged)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomTextButton(
                        text = stringResource(id = R.string.content_reset),
                        containerColor = Color.Unspecified,
                        contentColor = PomoroDoTheme.colorScheme.onBackground,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        verticalPadding = 8.dp,
                        horizontalPadding = 20.dp,
                        onClick = { onDismissRequest(false) }
                    )
                    CustomTextButton(
                        text = stringResource(id = confirmTextId),
                        containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                        contentColor = Color.White,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        verticalPadding = 8.dp,
                        horizontalPadding = 20.dp,
                        enabled = confirmEnable,
                        onClick = onConfirmation,
                        disabledContentColor = Color.White,
                        disabledContainerColor = PomoroDoTheme.colorScheme.dialogGray70
                    )
                }
            }
        }
    }
}

@Composable
private fun TodoList(
    todoList: List<TodoData>,
    onStateChanged: (index: Int, todoState: TodoState) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        itemsIndexed(items = todoList, key = { _, todoData -> todoData.id }) { index, todoItem ->
            TodoItem(
                iconSize = 24,
                todoData = todoItem,
                onStateChanged = { todoState -> onStateChanged(index, todoState) },
                textStyle = PomoroDoTheme.typography.laundryGothicRegular16
            )
        }
    }
}

@Preview
@Composable
fun PreviewDialog() {
    PomoroDoTheme {
        TodoListDialog(
            title = "할 일 목록",
            todoList = todoList,
            confirmTextId = R.string.content_ok,
            onConfirmation = { /*TODO*/ },
            onDismissRequest = { /*TODO*/ },
            onTodoStateChanged = { index, todoState -> /*TODO*/ }
        )
    }
}
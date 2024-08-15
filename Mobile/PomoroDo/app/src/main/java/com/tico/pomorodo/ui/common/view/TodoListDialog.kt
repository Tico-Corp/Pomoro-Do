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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tico.pomorodo.R
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
 * @param title The title of the dialog.
 * @param todoList The list of to-do items to display in the dialog.
 * @param confirmTextId The resource ID for the text on the confirm button.
 * @param onConfirmation Function to run when the confirm button is clicked.
 * @param onDismissRequest Function to run when the outside of dialog or back button is clicked.
 */

@Composable
fun TodoListDialog(
    title: String,
    todoList: List<TodoData>,
    @StringRes confirmTextId: Int,
    onConfirmation: (List<TodoData>) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val colors = CardDefaults.cardColors(containerColor = PomoroDoTheme.colorScheme.dialogSurface)
    val textColor = PomoroDoTheme.colorScheme.onBackground
    val newTodoList = remember { todoList.toMutableStateList() }

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

                TodoList(
                    todoList = newTodoList,
                    onStateChanged = { index: Int, todoState: TodoState ->
                        changeTodoState(newTodoList, index, todoState)
                    }
                )

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
                        onClick = {
                            newTodoList.clear()
                            newTodoList.addAll(todoList)
                        }
                    )
                    CustomTextButton(
                        text = stringResource(id = confirmTextId),
                        containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                        contentColor = Color.White,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                        verticalPadding = 8.dp,
                        horizontalPadding = 20.dp,
                        onClick = { onConfirmation(newTodoList) }
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        itemsIndexed(items = todoList, key = { _, todoData -> todoData.id }) { index, todoItem ->
            TodoItem(
                modifier = Modifier.fillMaxWidth(),
                iconSize = 24,
                todoData = todoItem,
                onStateChanged = { onStateChanged(index, todoItem.status) },
                textStyle = PomoroDoTheme.typography.laundryGothicRegular16
            )
        }
    }
}

fun changeTodoState(newTodoList: SnapshotStateList<TodoData>, todoIndex: Int, state: TodoState) {
    val newState = when (state) {
        TodoState.UNCHECKED -> TodoState.CHECKED
        TodoState.CHECKED -> TodoState.GOING
        TodoState.GOING -> TodoState.UNCHECKED
    }
    val newItem = newTodoList[todoIndex].copy(status = newState)
    newTodoList[todoIndex] = newItem
}
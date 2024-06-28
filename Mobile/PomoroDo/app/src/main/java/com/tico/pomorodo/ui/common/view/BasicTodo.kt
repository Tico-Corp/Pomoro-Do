package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.ui.theme.IC_TODO_CHECKED
import com.tico.pomorodo.ui.theme.IC_TODO_GOING
import com.tico.pomorodo.ui.theme.IC_TODO_UNCHECKED
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun BasicTodoItem(
    todoData: TodoData,
    onStateChanged: (TodoState) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TodoCheckBox(state = todoData.state, onStateChanged = onStateChanged)
        SimpleText(
            modifier = Modifier.weight(1f),
            text = todoData.name,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun TodoCheckBox(state: TodoState, onStateChanged: (TodoState) -> Unit) {
    SimpleIcon(
        modifier = Modifier.clickable {
            onStateChanged(state)
        },
        size = 26,
        imageVector = when (state) {
            TodoState.UNCHECKED -> PomoroDoTheme.iconPack[IC_TODO_UNCHECKED]!!
            TodoState.GOING -> PomoroDoTheme.iconPack[IC_TODO_GOING]!!
            TodoState.CHECKED -> PomoroDoTheme.iconPack[IC_TODO_CHECKED]!!
        },
        contentDescriptionId = when (state) {
            TodoState.UNCHECKED -> R.string.content_todo_unchecked
            TodoState.GOING -> R.string.content_todo_going
            TodoState.CHECKED -> R.string.content_todo_checked
        }
    )
}

enum class TodoState {
    UNCHECKED, GOING, CHECKED
}
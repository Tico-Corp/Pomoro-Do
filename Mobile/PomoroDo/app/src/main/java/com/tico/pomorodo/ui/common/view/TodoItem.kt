package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
import com.tico.pomorodo.ui.theme.IC_TODO_CHECKED
import com.tico.pomorodo.ui.theme.IC_TODO_GOING
import com.tico.pomorodo.ui.theme.IC_TODO_UNCHECKED
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    iconSize: Int,
    todoData: TodoData,
    enabled: Boolean = true,
    textStyle: TextStyle,
    onStateChanged: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.clickableWithoutRipple(enabled) { onStateChanged?.invoke() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        TodoCheckBox(
            size = iconSize,
            state = todoData.status,
            enabled = enabled,
            onStateChanged = { onStateChanged?.invoke() })
        SimpleText(
            modifier = Modifier,
            text = todoData.title,
            style = textStyle,
            color = PomoroDoTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun TodoCheckBox(
    size: Int,
    state: TodoState,
    onStateChanged: () -> Unit,
    enabled: Boolean
) {
    SimpleIcon(
        modifier = Modifier.clickableWithoutRipple(enabled) {
            onStateChanged()
        },
        size = size,
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
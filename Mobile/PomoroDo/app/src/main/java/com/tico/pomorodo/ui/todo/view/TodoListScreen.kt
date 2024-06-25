package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.ui.common.view.CustomDropdownMenuItem
import com.tico.pomorodo.ui.common.view.CustomTextField
import com.tico.pomorodo.ui.common.view.DropdownMenuNoPaddingVertical
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.iconpack.IcFavoriteFilled
import com.tico.pomorodo.ui.iconpack.IcGroup
import com.tico.pomorodo.ui.theme.IC_ADD_TODO
import com.tico.pomorodo.ui.theme.IC_TODO_CHECKED
import com.tico.pomorodo.ui.theme.IC_TODO_GOING
import com.tico.pomorodo.ui.theme.IC_TODO_MORE_INFO
import com.tico.pomorodo.ui.theme.IC_TODO_UNCHECKED
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun TotalFocusStatus(hour: Int, minute: Int, second: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            SimpleText(
                modifier = Modifier,
                textId = R.string.title_total_focus_time_text,
                style = PomoroDoTheme.typography.laundryGothicBold16,
                color = PomoroDoTheme.colorScheme.onBackground
            )
            SimpleText(
                modifier = Modifier,
                text = stringResource(id = R.string.title_total_focus_time, hour, minute, second),
                color = PomoroDoTheme.colorScheme.primaryContainer,
                style = PomoroDoTheme.typography.laundryGothicBold16
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            SimpleText(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        PomoroDoTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                textId = R.string.content_view_history,
                style = PomoroDoTheme.typography.laundryGothicRegular12,
                color = PomoroDoTheme.colorScheme.secondary
            )
        }

    }
}

@Composable
fun CategoryTag(
    title: String,
    groupNumber: Int,
    isAddButton: Boolean = true,
    onAddClicked: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .clickable { onAddClicked?.invoke() }
            .background(
                PomoroDoTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SimpleText(
            modifier = Modifier,
            text = title,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.secondary
        )
        if (groupNumber > 0) {
            SimpleText(
                modifier = Modifier,
                text = stringResource(id = R.string.content_add_todo_group_number, groupNumber),
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.secondary
            )
        }
        if (isAddButton) {
            Image(
                imageVector = PomoroDoTheme.iconPack[IC_ADD_TODO]!!,
                contentDescription = null
            )
        }
    }
}

@Composable
fun TodoMake(
    callback: () -> Unit,
    inputText: String,
    onValueChange: (String) -> Unit,
) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        unfocusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        disabledTextColor = PomoroDoTheme.colorScheme.gray10,
        errorTextColor = PomoroDoTheme.colorScheme.error50,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        cursorColor = PomoroDoTheme.colorScheme.primaryContainer,
        unfocusedIndicatorColor = PomoroDoTheme.colorScheme.onBackground,
        focusedIndicatorColor = PomoroDoTheme.colorScheme.primaryContainer
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.Top
    ) {
        SimpleIcon(
            modifier = Modifier,
            size = 26,
            imageVector = PomoroDoTheme.iconPack[IC_TODO_UNCHECKED]!!,
            contentDescriptionId = R.string.content_todo_unchecked
        )
        CustomTextField(
            modifier = Modifier.weight(1f),
            value = inputText,
            onValueChange = onValueChange,
            placeholder = {
                SimpleText(
                    text = stringResource(id = R.string.content_todo_placehold),
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.gray50
                )
            },
            callback = callback,
            colors = textFieldColors,
            singleLine = false
        )
    }
}

@Composable
fun TodoItem(
    todoData: TodoData,
    isFriend: Boolean,
    isGroup: Boolean,
    onStateChanged: (TodoState) -> Unit,
    onMoreInfoEditClicked: () -> Unit,
    onMoreInfoDeleteClicked: () -> Unit,
    onGroupClicked: () -> Unit,
    onLikedClicked: () -> Unit,
) {
    var showMoreInfo by remember { mutableStateOf(false) }

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
        if (isGroup) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleIconButton(
                    size = 16,
                    imageVector = IconPack.IcGroup,
                    contentDescriptionId = R.string.content_ic_group,
                    enabled = true,
                    onClickedListener = onGroupClicked
                )
                SimpleText(
                    modifier = Modifier,
                    text = todoData.completeGroupNumber.toString(),
                    style = PomoroDoTheme.typography.laundryGothicRegular10,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
            }
        }
        if (todoData.likedNumber > 0) {
            Column(
                modifier = Modifier.clickable {},
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleIconButton(
                    size = 16,
                    imageVector = IconPack.IcFavoriteFilled,
                    contentDescriptionId = R.string.content_ic_favorite_filled,
                    onClickedListener = onLikedClicked,
                    enabled = isFriend
                )

                SimpleText(
                    modifier = Modifier,
                    text = todoData.likedNumber.toString(),
                    style = PomoroDoTheme.typography.laundryGothicRegular10,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
            }
        }
        Column {
            SimpleIconButton(
                modifier = Modifier,
                size = 15,
                imageVector = PomoroDoTheme.iconPack[IC_TODO_MORE_INFO]!!,
                contentDescriptionId = R.string.content_ic_todo_more_info,
                onClickedListener = {
                    showMoreInfo = true
                },
                enabled = true
            )
            TodoDropDownMoreInfo(
                showMoreInfo = showMoreInfo,
                onShowMoreInfoChange = { showMoreInfo = it },
                onMoreInfoEditClicked = onMoreInfoEditClicked,
                onMoreInfoDeleteClicked = onMoreInfoDeleteClicked
            )
        }
    }
}

@Composable
fun TodoDropDownMoreInfo(
    showMoreInfo: Boolean,
    onShowMoreInfoChange: (Boolean) -> Unit,
    onMoreInfoEditClicked: () -> Unit,
    onMoreInfoDeleteClicked: () -> Unit
) {
    DropdownMenuNoPaddingVertical(
        expanded = showMoreInfo,
        onDismissRequest = { onShowMoreInfoChange(false) },
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
    ) {
        CustomDropdownMenuItem(
            textId = R.string.content_todo_more_info_edit,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular10,
            onClick = {
                onMoreInfoEditClicked()
                onShowMoreInfoChange(false)
            },
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 18.dp)
        )
        HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
        CustomDropdownMenuItem(
            textId = R.string.content_todo_more_info_delete,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular10,
            onClick = {
                onMoreInfoDeleteClicked()
                onShowMoreInfoChange(false)
            },
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 18.dp)
        )
    }
}

@Composable
private fun TodoCheckBox(state: TodoState, onStateChanged: (TodoState) -> Unit) {
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
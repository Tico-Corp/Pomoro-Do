package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.ui.common.view.ProfileHorizontal
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GroupBottomSheet(
    title: String,
    sheetState: SheetState,
    onShowBottomSheetChange: (Boolean) -> Unit,
    completedList: List<User>,
    incompletedList: List<User>,
    totalNumber: Int,
    isClicked: Boolean,
    onClicked: (Int) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onShowBottomSheetChange(false)
        },
        sheetState = sheetState,
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
        dragHandle = {
            Surface(
                modifier = Modifier.padding(top = 8.dp, bottom = 18.dp),
                color = PomoroDoTheme.colorScheme.dialogGray70,
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Box(Modifier.size(width = 63.dp, height = 3.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SimpleText(
                text = title,
                isEllipsis = true,
                style = PomoroDoTheme.typography.laundryGothicBold20,
                color = PomoroDoTheme.colorScheme.onBackground
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    SimpleText(
                        text = stringResource(
                            id = R.string.title_group_complete,
                            completedList.size,
                            totalNumber
                        ),
                        isEllipsis = true,
                        style = PomoroDoTheme.typography.laundryGothicBold16,
                        color = PomoroDoTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                if (completedList.isEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                } else {
                    items(completedList) { user ->
                        ProfileHorizontal(
                            modifier = Modifier.fillMaxWidth(),
                            size = 36,
                            name = user.name,
                            textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                            isClicked = isClicked,
                            onClicked = { onClicked(user.id) }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    HorizontalDivider(color = PomoroDoTheme.colorScheme.dialogGray90)
                    Spacer(modifier = Modifier.height(6.dp))
                }
                item {
                    SimpleText(
                        text = stringResource(
                            id = R.string.title_group_incomplete,
                            incompletedList.size,
                            totalNumber
                        ),
                        isEllipsis = true,
                        style = PomoroDoTheme.typography.laundryGothicBold16,
                        color = PomoroDoTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                if (incompletedList.isEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                } else {
                    items(incompletedList) { user ->
                        ProfileHorizontal(
                            modifier = Modifier.fillMaxWidth(),
                            size = 36,
                            name = user.name,
                            textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                            isClicked = isClicked,
                            onClicked = { onClicked(user.id) }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
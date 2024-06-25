package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
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
) {
    ModalBottomSheet(
        onDismissRequest = {
            onShowBottomSheetChange(false)
        },
        sheetState = sheetState,
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
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
                items(completedList) {
                    ProfileHorizontal(
                        size = 36,
                        name = it.name,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14
                    )
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
                items(incompletedList) {
                    ProfileHorizontal(
                        size = 36,
                        name = it.name,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
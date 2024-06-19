package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.local.datasource.DataSource
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
    /*// Sheet content
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }) {

            }*/
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupBottomSheetPreview() {
    PomoroDoTheme() {
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show bottom sheet") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        showBottomSheet = true
                    }
                )
            }
        ) { contentPadding ->
            Surface(modifier = Modifier.padding(contentPadding)) {
                if (showBottomSheet) {
                    GroupBottomSheet(
                        title = "title 1",
                        sheetState = sheetState,
                        onShowBottomSheetChange = { showBottomSheet = it },
                        completedList = DataSource.userList,
                        incompletedList = DataSource.userList,
                        totalNumber = 5,
                    )
                }
            }
        }
    }
}
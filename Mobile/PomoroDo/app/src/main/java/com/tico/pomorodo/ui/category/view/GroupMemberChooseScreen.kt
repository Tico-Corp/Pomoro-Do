package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.ui.category.viewModel.CategoryViewModel
import com.tico.pomorodo.ui.common.view.CustomTextField
import com.tico.pomorodo.ui.common.view.ProfileHorizontal
import com.tico.pomorodo.ui.common.view.ProfileVertical
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.addFocusCleaner
import com.tico.pomorodo.ui.common.view.toSelectedUser
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcGroupSelectedChecked
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcNoSearch
import com.tico.pomorodo.ui.theme.IC_ALL_CLEAN
import com.tico.pomorodo.ui.theme.IC_GROUP_SELECTED_UNCHECKED
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_SELECTED_GROUP_MEMBER_CANCEL
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Preview
@Composable
fun GroupMemberChooseScreenRoute(viewModel: CategoryViewModel = viewModel()) {
    val selectedList =
        remember { mutableStateListOf<SelectedUser>().apply { addAll(DataSource.userList.map { it.toSelectedUser() }) } }
    var searchName by rememberSaveable { mutableStateOf("") }
    val filteredList = if (searchName.isEmpty()) {
        selectedList
    } else {
        selectedList.filter { it.name.contains(searchName, ignoreCase = true) }
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    PomoroDoTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager) {
                    keyboardController?.hide()
                },
            containerColor = PomoroDoTheme.colorScheme.background,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                CategoryTopBar(
                    titleTextId = R.string.title_group_member_choose,
                    iconString = IC_OK,
                    disableIconString = IC_UNOK,
                    enabled = selectedList.any { it.selected },
                    descriptionId = R.string.content_ic_ok,
                    onClickedListener = {
                        viewModel.setGroupMembers(selectedList)
                    },
                    onBackClickedListener = {}
                )
                GroupMemberChooseScreen(
                    selectedList = selectedList,
                    searchName = searchName,
                    onSearchNameChanged = { searchName = it },
                    filteredList = filteredList,
                )
            }
        }
    }
}

@Composable
fun GroupMemberChooseScreen(
    selectedList: SnapshotStateList<SelectedUser>,
    searchName: String,
    onSearchNameChanged: (String) -> Unit,
    filteredList: List<SelectedUser>,
) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        unfocusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        disabledTextColor = PomoroDoTheme.colorScheme.dialogGray20,
        errorTextColor = PomoroDoTheme.colorScheme.error50,
        focusedContainerColor = PomoroDoTheme.colorScheme.dialogGray90,
        unfocusedContainerColor = PomoroDoTheme.colorScheme.dialogGray90,
        cursorColor = PomoroDoTheme.colorScheme.primaryContainer,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(selectedList) { index, user ->
                if (user.selected) {
                    Box(modifier = Modifier.clickable {
                        selectedList[index] = user.copy(selected = false)
                    }) {
                        ProfileVertical(
                            size = 42,
                            name = user.name,
                            textStyle = PomoroDoTheme.typography.laundryGothicRegular10,
                            defaultProfileUri = user.profileUrl
                        )
                        SimpleIcon(
                            modifier = Modifier.align(Alignment.TopEnd),
                            size = 13,
                            imageVector = PomoroDoTheme.iconPack[IC_SELECTED_GROUP_MEMBER_CANCEL]!!,
                            contentDescriptionId = R.string.content_ic_selected_group_member_cancel
                        )
                    }
                    if (index != selectedList.size - 1)
                        Spacer(modifier = Modifier.widthIn(min = 10.dp, max = 16.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchName,
            onValueChange = { onSearchNameChanged(it) },
            placeholder = {
                SimpleText(
                    text = stringResource(id = R.string.content_search_group_member),
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.gray50
                )
            },
            colors = textFieldColors,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp
            ),
            shape = RoundedCornerShape(5.dp),
            trailingIcon = {
                if (searchName.isNotBlank()) {
                    SimpleIconButton(
                        size = 34,
                        imageVector = PomoroDoTheme.iconPack[IC_ALL_CLEAN]!!,
                        contentDescriptionId = R.string.content_ic_all_clean,
                        enabled = true,
                        onClickedListener = { onSearchNameChanged("") }
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (filteredList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                SimpleIcon(
                    size = 90,
                    imageVector = IconPack.IcNoSearch,
                    contentDescriptionId = R.string.content_ic_no_search
                )
                SimpleText(
                    textId = R.string.content_no_search_message,
                    style = PomoroDoTheme.typography.laundryGothicBold24,
                    color = PomoroDoTheme.colorScheme.gray50
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(filteredList) { index, user ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            val selectedIndex = selectedList.indexOf(user)
                            if (selectedIndex >= 0) {
                                selectedList[selectedIndex] =
                                    user.copy(selected = !user.selected)
                            }
                        }
                    ) {
                        ProfileHorizontal(
                            size = 36,
                            name = user.name,
                            textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                            defaultProfileUri = user.profileUrl
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (user.selected) {
                            SimpleIcon(
                                size = 20,
                                imageVector = IconPack.IcGroupSelectedChecked,
                                contentDescriptionId = R.string.content_ic_group_selected_checked
                            )
                        } else {
                            SimpleIcon(
                                size = 20,
                                imageVector = PomoroDoTheme.iconPack[IC_GROUP_SELECTED_UNCHECKED]!!,
                                contentDescriptionId = R.string.content_ic_group_selected_checked
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (index != filteredList.size - 1) {
                        HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
                    }
                }
            }
        }
    }
}
package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.ui.category.viewModel.CategoryViewModel
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTextField
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.addFocusCleaner
import com.tico.pomorodo.ui.common.view.toSelectedUser
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InfoCategoryScreenRoute(viewModel: CategoryViewModel = viewModel()) {
    val openSettingsOptionSheetState = rememberModalBottomSheetState()
    val checkGroupMemberSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var showOpenSettingsBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showCheckGroupMemberBottomSheet by rememberSaveable { mutableStateOf(false) }
    val isGroupReader by rememberSaveable { mutableStateOf(false) }
    var groupDeleteDialogVisible by rememberSaveable { mutableStateOf(false) }
    var groupOutDialogVisible by rememberSaveable { mutableStateOf(false) }

    val title by viewModel.title.collectAsState()
    val type by viewModel.type.collectAsState()
    val openSettingOption by viewModel.openSettingOption.collectAsState()
    val groupMembers by viewModel.groupMembers.collectAsState()
    val selectedGroupMembers by rememberSaveable {
        mutableStateOf<List<SelectedUser>>(DataSource.userList.map { it.toSelectedUser() })
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
                    .verticalScroll(rememberScrollState())
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
                    modifier = Modifier,
                    titleTextId = R.string.title_info_category,
                    iconString = IC_OK,
                    disableIconString = IC_UNOK,
                    enabled = selectedGroupMembers.any { it.selected } && viewModel.validateInput(),
                    descriptionId = R.string.content_ic_ok,
                    onClickedListener = {
                        viewModel.setGroupMembers(selectedGroupMembers)
                    },
                    onBackClickedListener = {}
                )
                if (showCheckGroupMemberBottomSheet) {
                    CheckGroupMemberBottomSheet(
                        sheetState = checkGroupMemberSheetState,
                        onShowBottomSheetChange = { showCheckGroupMemberBottomSheet = it },
                        memberList = groupMembers
                    )
                }
                if (showOpenSettingsBottomSheet) {
                    OpenSettingsBottomSheet(
                        title = stringResource(id = R.string.title_open_settings),
                        sheetState = openSettingsOptionSheetState,
                        openSettingOption = openSettingOption,
                        onShowBottomSheetChange = { showOpenSettingsBottomSheet = it },
                        onOkButtonClicked = {
                            viewModel.setOpenSettingOption(it)
                            scope.launch { openSettingsOptionSheetState.hide() }
                                .invokeOnCompletion {
                                    if (!openSettingsOptionSheetState.isVisible) {
                                        showOpenSettingsBottomSheet = false
                                    }
                                }
                        }
                    )
                }
                InfoCategoryScreen(
                    title = title,
                    type = type,
                    groupNumber = selectedGroupMembers.filter { it.selected }.size,
                    openSettingOption = if (!type) OpenSettings.GROUP else openSettingOption,
                    groupReader = "모카커피짱귀엽",
                    onTitleChanged = viewModel::setTitle,
                    onShowOpenSettingsBottomSheetChange = {
                        showOpenSettingsBottomSheet = it
                    },
                    onGroupMemberChooseClicked = {
                        // navigate to choose group member
                    },
                    onShowCheckGroupMemberBottomSheetChange = {
                        showCheckGroupMemberBottomSheet = it
                    },
                    isGroupReader = isGroupReader,
                    onGroupDeleteClicked = { groupDeleteDialogVisible = true },
                    onGroupOutClicked = { groupOutDialogVisible = true }
                )
                if (groupDeleteDialogVisible) {
                    GroupDeleteFirstDialog(
                        onConfirmation = { },
                        onDismissRequest = { groupDeleteDialogVisible = it }
                    )
                }
                if (groupOutDialogVisible) {
                    GroupOutDialog(
                        groupName = title,
                        onAllDeleteClicked = { /*TODO*/ },
                        onIncompletedTodoDeleteClicked = { /*TODO*/ },
                        onNoDeleteClicked = { /*TODO*/ },
                        onDismissRequest = {groupOutDialogVisible = it }
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCategoryScreen(
    title: String,
    type: Boolean,
    groupNumber: Int,
    onTitleChanged: (String) -> Unit,
    openSettingOption: OpenSettings,
    groupReader: String? = null,
    isGroupReader: Boolean,
    onShowOpenSettingsBottomSheetChange: (Boolean) -> Unit,
    onGroupMemberChooseClicked: () -> Unit,
    onShowCheckGroupMemberBottomSheetChange: (Boolean) -> Unit,
    onGroupOutClicked: () -> Unit,
    onGroupDeleteClicked: () -> Unit,
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
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        color = PomoroDoTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { onTitleChanged(it) },
                placeholder = {
                    SimpleText(
                        text = stringResource(id = R.string.content_input_category_title),
                        style = PomoroDoTheme.typography.laundryGothicRegular14,
                        color = PomoroDoTheme.colorScheme.gray50
                    )
                },
                singleLine = true,
                colors = textFieldColors
            )
            CategoryType(type)
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            if (groupReader != null) {
                GroupReader(groupReader)
                HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            }
            CategoryOpenSettings(
                iconString = openSettingOption.iconString,
                textId = openSettingOption.textId,
                enabled = openSettingOption.enabled,
                descriptionId = openSettingOption.descriptionId,
                onClicked = { onShowOpenSettingsBottomSheetChange(true) },
                isGroupInfo = !type
            )
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            if (type) {
                CustomTextButton(
                    text = stringResource(id = R.string.content_do_delete),
                    containerColor = PomoroDoTheme.colorScheme.error50,
                    contentColor = Color.White,
                    textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
                    verticalPadding = 12.dp,
                    onClick = {}
                )
            } else {
                CategoryGroupNumber(
                    groupNumber = groupNumber,
                    onClicked = {
                        if (isGroupReader) {
                            onGroupMemberChooseClicked()
                        } else {
                            onShowCheckGroupMemberBottomSheetChange(true)
                        }
                    },
                    isGroupReader = isGroupReader
                )
                HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
                if (isGroupReader) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        CustomTextButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = R.string.content_group_out),
                            containerColor = Color.Unspecified,
                            textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
                            contentColor = PomoroDoTheme.colorScheme.error50,
                            borderColor = PomoroDoTheme.colorScheme.error50,
                            onClick = onGroupOutClicked,
                            verticalPadding = 12.dp,
                        )
                        CustomTextButton(
                            modifier = Modifier.weight(1f),
                            containerColor = PomoroDoTheme.colorScheme.error50,
                            text = stringResource(id = R.string.content_do_delete),
                            textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
                            contentColor = Color.White,
                            onClick = onGroupDeleteClicked,
                            verticalPadding = 12.dp,
                        )
                    }
                } else {
                    CustomTextButton(
                        text = stringResource(id = R.string.content_group_out),
                        containerColor = Color.Unspecified,
                        contentColor = PomoroDoTheme.colorScheme.error50,
                        borderColor = PomoroDoTheme.colorScheme.error50,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
                        onClick = onGroupOutClicked,
                        verticalPadding = 12.dp,
                    )
                }
            }
        }
    }
}

@Composable
private fun GroupReader(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleText(
            textId = R.string.title_group_reader,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        SimpleText(
            text = name,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun CategoryType(type: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleText(
            textId = R.string.title_type,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        SimpleText(
            textId = if (type) R.string.content_category_normal else R.string.content_category_group,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
    }
}
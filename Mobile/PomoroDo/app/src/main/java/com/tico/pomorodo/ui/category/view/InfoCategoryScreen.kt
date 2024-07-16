package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.navigation.MainNavigationDestination
import com.tico.pomorodo.ui.category.viewModel.InfoCategoryViewModel
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTextField
import com.tico.pomorodo.ui.common.view.CustomTopAppBar
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.addFocusCleaner
import com.tico.pomorodo.ui.common.view.getNoSpace
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCategoryScreenRoute(
    viewModel: InfoCategoryViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToGroupMemberChoose: (String) -> Unit,
) {
    val openSettingsOptionSheetState = rememberModalBottomSheetState()
    val checkGroupMemberSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var showOpenSettingsBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showCheckGroupMemberBottomSheet by rememberSaveable { mutableStateOf(false) }
    var groupDeleteFirstDialogVisible by rememberSaveable { mutableStateOf(false) }
    var groupDeleteSecondDialogVisible by rememberSaveable { mutableStateOf(false) }
    var groupOutDialogVisible by rememberSaveable { mutableStateOf(false) }
    var normalOutDialogVisible by rememberSaveable { mutableStateOf(false) }

    val category by viewModel.category.collectAsState()
    val selectedGroupMembers by viewModel.selectedGroupMembers.collectAsState()

    var deleteDialogInputText by rememberSaveable {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (category == null) {
        // TODO: 데이터 로딩 화면
    } else {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager) {
                    keyboardController?.hide()
                },
            color = PomoroDoTheme.colorScheme.background,
        ) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
            ) {
                CustomTopAppBar(
                    modifier = Modifier,
                    titleTextId = R.string.title_info_category,
                    iconString = IC_OK,
                    disableIconString = IC_UNOK,
                    enabled = selectedGroupMembers.any { it.selected } && viewModel.validateInput(),
                    descriptionId = R.string.content_ic_ok,
                    onClickedListener = {
                        navigateToCategory()
                    },
                    onBackClickedListener = navigateToBack
                )
                if (showCheckGroupMemberBottomSheet) {
                    category!!.groupMember?.let {
                        CheckGroupMemberBottomSheet(
                            sheetState = checkGroupMemberSheetState,
                            onShowBottomSheetChange = { showCheckGroupMemberBottomSheet = it },
                            memberList = it
                        )
                    }
                }
                if (showOpenSettingsBottomSheet) {
                    OpenSettingsBottomSheet(
                        title = stringResource(id = R.string.title_open_settings),
                        sheetState = openSettingsOptionSheetState,
                        openSettingOption = category!!.openSettings,
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
                if (groupDeleteFirstDialogVisible) {
                    GroupDeleteFirstDialog(
                        onConfirmation = {
                            groupDeleteFirstDialogVisible = false
                            groupDeleteSecondDialogVisible = true
                        },
                        onDismissRequest = { groupDeleteFirstDialogVisible = false }
                    )
                }
                if (groupDeleteSecondDialogVisible) {
                    GroupDeleteSecondDialog(
                        groupName = category!!.title,
                        enabled = deleteDialogInputText == category!!.title.getNoSpace(),
                        value = deleteDialogInputText,
                        onValueChange = { deleteDialogInputText = it },
                        onConfirmation = { /*TODO: 그룹 카테고리 삭제 로직*/ },
                        onDismissRequest = { groupDeleteSecondDialogVisible = false })
                }
                if (groupOutDialogVisible) {
                    CategoryOutDialog(
                        title = stringResource(id = R.string.title_group_out),
                        content = stringResource(
                            id = R.string.content_group_out_message,
                            category!!.title
                        ),
                        onAllDeleteClicked = { /*TODO: 그룹 카테고리 할 일 모두 삭제 로직*/ },
                        onIncompletedTodoDeleteClicked = { /*TODO: 그룹 카테고리 할 일 중 미완료 할 일만 삭제 로직*/ },
                        onNoDeleteClicked = { /*TODO: 그룹 카테고리 할 일은 삭제 안하는 로직*/ },
                        onDismissRequest = { groupOutDialogVisible = false }
                    )
                }
                if (normalOutDialogVisible) {
                    CategoryOutDialog(
                        title = stringResource(id = R.string.title_category_delete),
                        content = stringResource(id = R.string.content_category_delete_message),
                        onAllDeleteClicked = { /*TODO: 일반 카테고리 할 일 모두 삭제 로직*/ },
                        onIncompletedTodoDeleteClicked = { /*TODO: 일반 카테고리 할 일 중 미완료 할 일만 삭제 로직*/ },
                        onNoDeleteClicked = { /*TODO: 일반 카테고리 할 일은 삭제 안하는 로직*/ },
                        onDismissRequest = { normalOutDialogVisible = false })
                }
                InfoCategoryScreen(
                    title = category!!.title,
                    type = category!!.type,
                    groupNumber = selectedGroupMembers.filter { it.selected }.size,
                    openSettingOption = if (category!!.type == CategoryType.GROUP) OpenSettings.GROUP else category!!.openSettings,
                    groupReader = category!!.groupReader,
                    onTitleChanged = viewModel::setTitle,
                    onShowOpenSettingsBottomSheetChange = {
                        showOpenSettingsBottomSheet = it
                    },
                    onGroupMemberChooseClicked = {
                        navigateToGroupMemberChoose(
                            MainNavigationDestination.InfoCategory.name
                        )
                    },
                    onShowCheckGroupMemberBottomSheetChange = {
                        showCheckGroupMemberBottomSheet = it
                    },
                    isGroupReader = category!!.isGroupReader,
                    onGroupDeleteClicked = { groupDeleteFirstDialogVisible = true },
                    onGroupOutClicked = { groupOutDialogVisible = true },
                    onNormalDeletedClicked = { normalOutDialogVisible = true }
                )
            }
        }
    }
}

@Composable
fun InfoCategoryScreen(
    title: String,
    type: CategoryType,
    groupNumber: Int,
    onTitleChanged: (String) -> Unit,
    openSettingOption: OpenSettings,
    groupReader: String? = null,
    isGroupReader: Boolean?,
    onShowOpenSettingsBottomSheetChange: (Boolean) -> Unit,
    onGroupMemberChooseClicked: () -> Unit,
    onShowCheckGroupMemberBottomSheetChange: (Boolean) -> Unit,
    onGroupOutClicked: () -> Unit,
    onGroupDeleteClicked: () -> Unit,
    onNormalDeletedClicked: () -> Unit,
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
                colors = textFieldColors,
                textStyle = PomoroDoTheme.typography.laundryGothicRegular14
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
                isGroupInfo = type == CategoryType.GROUP
            )
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            if (type == CategoryType.NORMAL) {
                CustomTextButton(
                    text = stringResource(id = R.string.content_do_delete),
                    containerColor = PomoroDoTheme.colorScheme.error50,
                    contentColor = Color.White,
                    textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
                    verticalPadding = 12.dp,
                    onClick = onNormalDeletedClicked
                )
            } else {
                CategoryGroupNumber(
                    groupNumber = groupNumber,
                    onClicked = {
                        if (isGroupReader == true) {
                            onGroupMemberChooseClicked()
                        } else if (isGroupReader == false) {
                            onShowCheckGroupMemberBottomSheetChange(true)
                        }
                    },
                    isGroupReader = isGroupReader
                )
                HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
                if (isGroupReader == true) {
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
                } else if (isGroupReader == false) {
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
private fun CategoryType(type: CategoryType) {
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
            textId = if (type == CategoryType.NORMAL) R.string.content_category_normal else R.string.content_category_group,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
    }
}
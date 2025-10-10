package com.tico.pomorodo.ui.category.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
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
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.DeletionOption
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.ui.category.viewModel.CategoryInfoViewModel
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
fun CategoryInfoScreenRoute(
    viewModel: CategoryInfoViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    isOffline: Boolean,
    navigateToFollowTodoScreen: (Int) -> Unit
) {
    val openSettingsOptionSheetState = rememberModalBottomSheetState()
    val checkGroupMemberSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var showOpenSettingsBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showCheckGroupMemberBottomSheet by rememberSaveable { mutableStateOf(false) }
    var groupDeleteFirstDialogVisible by rememberSaveable { mutableStateOf(false) }
    var groupOutDialogVisible by rememberSaveable { mutableStateOf(false) }
    var personalDeleteDialogVisible by rememberSaveable { mutableStateOf(false) }
    var endOfEditingDialogVisible by remember { mutableStateOf(false) }

    val categoryState by viewModel.category.collectAsState()
    val title by viewModel.title.collectAsState()
    val openSettings by viewModel.openSettings.collectAsState()
    val selectedGroupMembers by viewModel.selectedGroupMembers.collectAsState()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val category = categoryState ?: run {
        LoadingScreen()
        return
    }
    val isReadOnly = remember(category, isOffline) { category.isReadOnly(isOffline) }

    BackHandler {
        if (isReadOnly) navigateToBack() else endOfEditingDialogVisible = true
    }
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
                actionIconString = if (isReadOnly) null else IC_OK,
                actionDisableIconString = if (isReadOnly) null else IC_UNOK,
                isActionEnabled = (selectedGroupMembers.any { it.selected } && category.type == CategoryType.GROUP && viewModel.validateInput())
                        || (category.type == CategoryType.PERSONAL && viewModel.validateInput()),
                actionIconDescriptionId = R.string.content_ic_ok,
                onActionClickedListener = {
                    viewModel.updateCategoryInfo()
                    navigateToBack()
                },
                onBackClickedListener = {
                    if (isReadOnly) navigateToBack() else endOfEditingDialogVisible = true
                }
            )
            if (showCheckGroupMemberBottomSheet) {
                category.groupMembers?.let { user ->
                    CheckGroupMemberBottomSheet(
                        sheetState = checkGroupMemberSheetState,
                        onShowBottomSheetChange = { showCheckGroupMemberBottomSheet = it },
                        memberList = user,
                        onClicked = { userId ->
                            showCheckGroupMemberBottomSheet = false
                            navigateToFollowTodoScreen(userId)
                        }
                    )
                }
            }
            if (showOpenSettingsBottomSheet) {
                OpenSettingsBottomSheet(
                    title = stringResource(id = R.string.title_open_settings),
                    sheetState = openSettingsOptionSheetState,
                    openSettingOption = openSettings,
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
            GroupCategoryDeleteDialog(
                groupDeleteFirstDialogVisible = groupDeleteFirstDialogVisible,
                category = category,
                setGroupDeleteFirstDialogVisible = { groupDeleteFirstDialogVisible = it },
                onGroupDeleteClicked = {
                    viewModel.deleteCategory(it)
                    navigateToBack()
                }
            )
            GroupCategoryOutDialog(
                groupOutDialogVisible = groupOutDialogVisible,
                category = category,
                setGroupOutDialogVisible = { groupOutDialogVisible = it },
                onAllDeleteClicked = {
                    groupOutDialogVisible = false
                    viewModel.outCategory(DeletionOption.DELETE_ALL)
                    navigateToBack()
                },
                onIncompleteTodoDeleteClicked = {
                    groupOutDialogVisible = false
                    viewModel.outCategory(DeletionOption.RETAIN_COMPLETED)
                    navigateToBack()
                },
                onNoDeleteClicked = {
                    groupOutDialogVisible = false
                    viewModel.outCategory(DeletionOption.RETAIN_ALL)
                    navigateToBack()
                }
            )
            PersonalDeleteDialog(
                personalDeleteDialogVisible = personalDeleteDialogVisible,
                setPersonalDeleteDialogVisible = { personalDeleteDialogVisible = it },
                onAllDeleteClicked = {
                    personalDeleteDialogVisible = false
                    viewModel.deleteCategory(DeletionOption.DELETE_ALL)
                    navigateToBack()
                },
                onIncompleteTodoDeleteClicked = {
                    personalDeleteDialogVisible = false
                    viewModel.deleteCategory(DeletionOption.RETAIN_COMPLETED)
                    navigateToBack()
                },
                onNoDeleteClicked = {
                    personalDeleteDialogVisible = false
                    viewModel.deleteCategory(DeletionOption.RETAIN_ALL)
                    navigateToBack()
                }
            )
            if (endOfEditingDialogVisible) {
                EndOfEditingDialog(
                    onDismissRequest = { endOfEditingDialogVisible = false },
                    onConfirmation = {
                        endOfEditingDialogVisible = false
                        navigateToBack()
                    }
                )
            }
            CategoryInfoScreen(
                isReadOnly = isReadOnly,
                isOffline = isOffline,
                title = title,
                type = category.type,
                groupMemberCount = selectedGroupMembers.filter { it.selected }.size,
                openSettingOption = if (category.type == CategoryType.GROUP) OpenSettings.GROUP
                else openSettings,
                ownerNickname = category.ownerNickname,
                onTitleChanged = viewModel::setTitle,
                onShowOpenSettingsBottomSheetChange = { showOpenSettingsBottomSheet = it },
                onShowCheckGroupMemberBottomSheetChange = { showCheckGroupMemberBottomSheet = it },
                ownerFlag = category.ownerFlag,
                onGroupDeleteClicked = { groupDeleteFirstDialogVisible = true },
                onGroupOutClicked = { groupOutDialogVisible = true },
                onPersonalDeletedClicked = { personalDeleteDialogVisible = true }
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    // TODO: 데이터 로딩 화면
}

@Composable
fun CategoryInfoScreen(
    title: String,
    type: CategoryType,
    groupMemberCount: Int,
    onTitleChanged: (String) -> Unit,
    openSettingOption: OpenSettings,
    ownerNickname: String? = null,
    ownerFlag: Boolean,
    onShowOpenSettingsBottomSheetChange: (Boolean) -> Unit,
    onShowCheckGroupMemberBottomSheetChange: (Boolean) -> Unit,
    onGroupOutClicked: () -> Unit,
    onGroupDeleteClicked: () -> Unit,
    onPersonalDeletedClicked: () -> Unit,
    isOffline: Boolean,
    isReadOnly: Boolean
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
            if (isReadOnly) {
                Column {
                    Spacer(Modifier.height(5.dp))
                    SimpleText(
                        text = title,
                        style = PomoroDoTheme.typography.laundryGothicRegular14,
                        color = PomoroDoTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(5.dp))
                }
            } else {
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
            }
            CategoryType(type)
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            if (ownerNickname != null) {
                GroupReader(ownerNickname)
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
            if (type == CategoryType.PERSONAL) {
                Spacer(Modifier.height(37.dp))
                if (!isOffline) {
                    CustomTextButton(
                        text = stringResource(id = R.string.content_do_delete),
                        containerColor = PomoroDoTheme.colorScheme.error50,
                        contentColor = Color.White,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
                        verticalPadding = 12.dp,
                        onClick = onPersonalDeletedClicked
                    )
                }
            } else {
                CategoryGroupNumber(
                    groupMemberCount = groupMemberCount,
                    onClicked = {
                        onShowCheckGroupMemberBottomSheetChange(true)
                    }
                )
                HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
                Spacer(Modifier.height(37.dp))
                if (!isOffline) {
                    if (ownerFlag) {
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
}

@Composable
private fun GroupReader(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleText(
            textId = R.string.title_group_reader,
            style = PomoroDoTheme.typography.laundryGothicBold14,
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
            style = PomoroDoTheme.typography.laundryGothicBold14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        SimpleText(
            textId = if (type == CategoryType.PERSONAL) R.string.content_category_personal else R.string.content_category_group,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
    }
}

private fun Category.isReadOnly(isOffline: Boolean): Boolean {
    return if (isOffline) {
        this.type == CategoryType.GROUP
    } else {
        this.type == CategoryType.GROUP && !ownerFlag
    }
}

@Composable
private fun GroupCategoryDeleteDialog(
    groupDeleteFirstDialogVisible: Boolean,
    setGroupDeleteFirstDialogVisible: (Boolean) -> Unit,
    category: Category,
    onGroupDeleteClicked: (DeletionOption) -> Unit
) {
    var deleteDialogInputText by rememberSaveable { mutableStateOf("") }
    var groupDeleteSecondDialogVisible by rememberSaveable { mutableStateOf(false) }
    var deleteOption: DeletionOption? by rememberSaveable { mutableStateOf(null) }

    if (groupDeleteFirstDialogVisible) {
        CategoryDeleteOptionDialog(
            title = stringResource(id = R.string.title_group_category_delete),
            content = stringResource(
                id = R.string.content_group_delete_first_message,
                category.title
            ),
            onAllDeleteClicked = {
                deleteOption = DeletionOption.DELETE_ALL
                setGroupDeleteFirstDialogVisible(false)
                groupDeleteSecondDialogVisible = true
            },
            onIncompleteTodoDeleteClicked = {
                deleteOption = DeletionOption.RETAIN_COMPLETED
                setGroupDeleteFirstDialogVisible(false)
                groupDeleteSecondDialogVisible = true
            },
            onNoDeleteClicked = {
                deleteOption = DeletionOption.RETAIN_ALL
                setGroupDeleteFirstDialogVisible(false)
                groupDeleteSecondDialogVisible = true
            },
            onDismissRequest = { setGroupDeleteFirstDialogVisible(false) }
        )
    }
    if (groupDeleteSecondDialogVisible) {
        GroupDeleteSecondDialog(
            groupName = category.title,
            enabled = deleteDialogInputText == category.title.getNoSpace(),
            value = deleteDialogInputText,
            onValueChange = { deleteDialogInputText = it },
            onConfirmation = {
                groupDeleteSecondDialogVisible = false
                deleteOption?.let {
                    onGroupDeleteClicked(it)
                }
            },
            onDismissRequest = { groupDeleteSecondDialogVisible = false })
    }
}

@Composable
private fun PersonalDeleteDialog(
    personalDeleteDialogVisible: Boolean,
    setPersonalDeleteDialogVisible: (Boolean) -> Unit,
    onAllDeleteClicked: () -> Unit,
    onIncompleteTodoDeleteClicked: () -> Unit,
    onNoDeleteClicked: () -> Unit,
) {
    if (personalDeleteDialogVisible) {
        CategoryDeleteOptionDialog(
            title = stringResource(id = R.string.title_category_delete),
            content = stringResource(id = R.string.content_category_delete_message),
            onAllDeleteClicked = onAllDeleteClicked,
            onIncompleteTodoDeleteClicked = onIncompleteTodoDeleteClicked,
            onNoDeleteClicked = onNoDeleteClicked,
            onDismissRequest = { setPersonalDeleteDialogVisible(false) })
    }
}

@Composable
private fun GroupCategoryOutDialog(
    groupOutDialogVisible: Boolean,
    category: Category,
    setGroupOutDialogVisible: (Boolean) -> Unit,
    onAllDeleteClicked: () -> Unit,
    onIncompleteTodoDeleteClicked: () -> Unit,
    onNoDeleteClicked: () -> Unit,
) {
    if (groupOutDialogVisible) {
        CategoryDeleteOptionDialog(
            title = stringResource(id = R.string.title_group_out),
            content = stringResource(
                id = R.string.content_group_out_message,
                category.title
            ),
            onAllDeleteClicked = onAllDeleteClicked,
            onIncompleteTodoDeleteClicked = onIncompleteTodoDeleteClicked,
            onNoDeleteClicked = onNoDeleteClicked,
            onDismissRequest = { setGroupOutDialogVisible(false) }
        )
    }
}
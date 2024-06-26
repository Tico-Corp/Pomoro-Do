package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.RadioButtonColors
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
import com.tico.pomorodo.ui.common.view.CustomTextField
import com.tico.pomorodo.ui.common.view.NoPaddingRadioButton
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.addFocusCleaner
import com.tico.pomorodo.ui.common.view.clickableWithoutRipple
import com.tico.pomorodo.ui.common.view.toSelectedUser
import com.tico.pomorodo.ui.theme.IC_ARROW_RIGHT
import com.tico.pomorodo.ui.theme.IC_CATEGORY_FOLLOWER_OPEN
import com.tico.pomorodo.ui.theme.IC_DROP_DOWN
import com.tico.pomorodo.ui.theme.IC_DROP_DOWN_DISABLE
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.launch

@Composable
fun AddCategoryScreen(
    title: String,
    type: Boolean,
    groupNumber: Int,
    onTitleChanged: (String) -> Unit,
    onTypeChanged: (Boolean) -> Unit,
    openSettingOption: OpenSettings,
    onShowOpenSettingsBottomSheetChange: (Boolean) -> Unit,
    onGroupMemberChooseClicked: () -> Unit,
) {
    val radioButtonColors = RadioButtonColors(
        selectedColor = PomoroDoTheme.colorScheme.onBackground,
        unselectedColor = PomoroDoTheme.colorScheme.onBackground,
        disabledSelectedColor = Color.Unspecified,
        disabledUnselectedColor = Color.Unspecified
    )
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
            .padding(horizontal = 18.dp)
    ) {
        Column(
            modifier = Modifier.background(PomoroDoTheme.colorScheme.background),
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
            CategoryType(type = type, colors = radioButtonColors, onTypeChanged = onTypeChanged)
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            CategoryOpenSettings(
                iconString = openSettingOption.iconString,
                textId = openSettingOption.textId,
                descriptionId = openSettingOption.descriptionId,
                enabled = openSettingOption.enabled,
                onClicked = { onShowOpenSettingsBottomSheetChange(true) },
            )
            HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            if (!type) {
                CategoryGroupNumber(
                    groupNumber = groupNumber,
                    onClicked = onGroupMemberChooseClicked,
                )
                HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
            }
        }
    }
}

@Composable
fun CategoryGroupNumber(groupNumber: Int, onClicked: () -> Unit, isGroupReader: Boolean = true) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SimpleText(
            textId = R.string.content_group_member,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.clickableWithoutRipple { onClicked() },
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleIcon(
                size = 15,
                imageVector = PomoroDoTheme.iconPack[IC_CATEGORY_FOLLOWER_OPEN]!!,
                contentDescriptionId = R.string.content_group_member
            )
            SimpleText(
                text = stringResource(id = R.string.content_group_member_number, groupNumber),
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.onBackground
            )
            SimpleIcon(
                size = 15,
                imageVector = if (isGroupReader) PomoroDoTheme.iconPack[IC_ARROW_RIGHT]!! else PomoroDoTheme.iconPack[IC_DROP_DOWN]!!,
                contentDescriptionId = R.string.content_full_open
            )
        }
    }
}

@Composable
fun CategoryOpenSettings(
    iconString: String,
    textId: Int,
    descriptionId: Int,
    enabled: Boolean,
    onClicked: () -> Unit,
    isGroupInfo: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SimpleText(
            textId = R.string.title_open_settings,
            style = PomoroDoTheme.typography.laundryGothicRegular14,
            color = PomoroDoTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.clickableWithoutRipple {
                    if (enabled) {
                        onClicked()
                    }
                },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleIcon(
                    size = 15,
                    imageVector = PomoroDoTheme.iconPack[iconString]!!,
                    contentDescriptionId = descriptionId
                )
                SimpleText(
                    textId = textId,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                if (!isGroupInfo) {
                    SimpleIcon(
                        size = 15,
                        imageVector = if (enabled) PomoroDoTheme.iconPack[IC_DROP_DOWN]!! else PomoroDoTheme.iconPack[IC_DROP_DOWN_DISABLE]!!,
                        contentDescriptionId = if (enabled) R.string.content_ic_drop_down else R.string.content_ic_drop_down_disable
                    )
                }
            }
            SimpleText(
                textId = R.string.content_only_group_message,
                style = PomoroDoTheme.typography.laundryGothicRegular10,
                color = PomoroDoTheme.colorScheme.gray50
            )
        }
    }
}

@Composable
private fun CategoryType(
    type: Boolean,
    colors: RadioButtonColors,
    onTypeChanged: (Boolean) -> Unit
) {
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
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NoPaddingRadioButton(
                selected = type,
                onClick = { onTypeChanged(true) },
                colors = colors,
                padding = PaddingValues(horizontal = 5.dp)
            )
            SimpleText(
                textId = R.string.content_category_normal,
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.onBackground
            )
            NoPaddingRadioButton(
                selected = !type,
                onClick = { onTypeChanged(false) },
                colors = colors,
                padding = PaddingValues(horizontal = 5.dp)
            )
            SimpleText(
                textId = R.string.content_category_group,
                style = PomoroDoTheme.typography.laundryGothicRegular14,
                color = PomoroDoTheme.colorScheme.onBackground
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddCategoryScreenRoute(viewModel: CategoryViewModel = viewModel()) {
    val openSettingsOptionSheetState = rememberModalBottomSheetState()
    var showOpenSettingsBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val title by viewModel.title.collectAsState()
    val type by viewModel.type.collectAsState()
    val openSettingOption by viewModel.openSettingOption.collectAsState()
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
                    titleTextId = R.string.title_add_category,
                    iconString = IC_OK,
                    disableIconString = IC_UNOK,
                    descriptionId = R.string.content_ic_add_category,
                    onClickedListener = {
                        viewModel.setGroupMembers(selectedGroupMembers)
                    },
                    onBackClickedListener = {},
                    enabled = selectedGroupMembers.any { it.selected } && viewModel.validateInput()
                )
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
                AddCategoryScreen(
                    title = title,
                    type = type,
                    groupNumber = selectedGroupMembers.filter { it.selected }.size,
                    openSettingOption = if (!type) OpenSettings.GROUP else openSettingOption,
                    onTypeChanged = viewModel::setType,
                    onTitleChanged = viewModel::setTitle,
                    onShowOpenSettingsBottomSheetChange = {
                        showOpenSettingsBottomSheet = it
                    },
                    onGroupMemberChooseClicked = {
                        //navigate to choose group member
                    }
                )
            }
        }
    }
}
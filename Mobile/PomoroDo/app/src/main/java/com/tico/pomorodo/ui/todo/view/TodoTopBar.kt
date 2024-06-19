package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomDropdownMenuItem
import com.tico.pomorodo.ui.common.view.DropdownMenuNoPaddingVertical
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.theme.IC_ADD_CATEGORY
import com.tico.pomorodo.ui.theme.IC_MORE_INFO
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onAddCategoryClicked: () -> Unit,
    onManageCategoryClicked: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val topBarColors = TopAppBarColors(
        containerColor = PomoroDoTheme.colorScheme.background,
        titleContentColor = PomoroDoTheme.colorScheme.onBackground,
        scrolledContainerColor = PomoroDoTheme.colorScheme.primaryContainer,
        actionIconContentColor = PomoroDoTheme.colorScheme.primaryContainer,
        navigationIconContentColor = Color.Unspecified,
    )
    TopAppBar(
        title = {},
        colors = topBarColors,
        actions = {
            IconButton(onClick = { showMenu = true }) {
                SimpleIcon(
                    size = 24,
                    imageVector = PomoroDoTheme.iconPack[IC_MORE_INFO]!!,
                    contentDescriptionId = R.string.content_ic_more_info
                )
                TodoDropDownMenu(
                    showMenu = showMenu,
                    onShowMenuChange = { showMenu = it },
                    onAddCategoryClicked = onAddCategoryClicked,
                    onManageCategoryClicked = onManageCategoryClicked
                )
            }
        }
    )
}

@Composable
fun TodoDropDownMenu(
    showMenu: Boolean,
    onShowMenuChange: (Boolean) -> Unit,
    onAddCategoryClicked: () -> Unit,
    onManageCategoryClicked: () -> Unit,
) {
    DropdownMenuNoPaddingVertical(
        expanded = showMenu,
        onDismissRequest = { onShowMenuChange(false) },
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
    ) {
        CustomDropdownMenuItem(
            textId = R.string.content_category_add,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular10,
            onClick = {
                onAddCategoryClicked()
                onShowMenuChange(false)
            },
            trailingIcon = PomoroDoTheme.iconPack[IC_ADD_CATEGORY]!!,
            trailingIconSize = 14,
            trailingIconContentDescriptionId = R.string.content_ic_add_category,
            contentPadding = PaddingValues(10.dp),
            textToIconSpacerWidth = 20
        )
        HorizontalDivider(color = PomoroDoTheme.colorScheme.gray90)
        CustomDropdownMenuItem(
            textId = R.string.content_category_manage,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular10,
            onClick = {
                onManageCategoryClicked()
                onShowMenuChange(false)
            },
            contentPadding = PaddingValues(10.dp)
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
    PomoroDoTheme() {
        Scaffold(
            topBar = {
                TopBar(onManageCategoryClicked = {}, onAddCategoryClicked = {})
            }
        ) {
            Surface(modifier = Modifier.padding(it)) {

            }
        }
    }
}
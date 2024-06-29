package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.InviteCategory
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.IC_ADD_CATEGORY
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.todo.view.CategoryTag
import com.tico.pomorodo.ui.todo.viewmodel.TodoViewModel

@Composable
fun CategoryScreen(
    normalCategoryList: List<Category>,
    groupCategoryList: List<Category>,
    inviteGroupCategoryList: List<InviteCategory>
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp, vertical = 16.dp),
        color = PomoroDoTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SimpleText(
                    textId = R.string.content_category_normal,
                    style = PomoroDoTheme.typography.laundryGothicBold16,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    normalCategoryList.forEach { category ->
                        CategoryTag(
                            title = category.title,
                            groupNumber = 0,
                            isAddButton = false,
                        )
                    }
                }
            }
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SimpleText(
                    textId = R.string.content_category_group,
                    style = PomoroDoTheme.typography.laundryGothicBold16,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    groupCategoryList.forEach { category ->
                        CategoryTag(
                            title = category.title,
                            groupNumber = 6,
                            isAddButton = false,
                        )
                    }
                }
            }
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SimpleText(
                    textId = R.string.content_category_invited_group,
                    style = PomoroDoTheme.typography.laundryGothicBold16,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    inviteGroupCategoryList.forEach { category ->
                        InvitedCategoryItem(
                            category.title,
                            category.groupReader,
                            onAcceptButtonClicked = {},
                            onRejectButtonClicked = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InvitedCategoryItem(
    title: String,
    groupReader: String,
    onAcceptButtonClicked: () -> Unit,
    onRejectButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    PomoroDoTheme.colorScheme.secondaryContainer,
                    RoundedCornerShape(5.dp)
                )
                .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                SimpleText(
                    modifier = Modifier,
                    text = title,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                SimpleText(
                    modifier = Modifier,
                    text = groupReader,
                    style = PomoroDoTheme.typography.laundryGothicRegular12,
                    color = PomoroDoTheme.colorScheme.secondary
                )
            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextButton(
                text = stringResource(id = R.string.content_reject),
                containerColor = Color.Unspecified,
                contentColor = PomoroDoTheme.colorScheme.onBackground,
                textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                verticalPadding = 5.dp,
                horizontalPadding = 12.dp,
                onClick = onRejectButtonClicked
            )
            CustomTextButton(
                text = stringResource(id = R.string.content_accept),
                textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                contentColor = Color.White,
                onClick = onAcceptButtonClicked,
                containerColor = PomoroDoTheme.colorScheme.primaryContainer,
                verticalPadding = 5.dp,
                horizontalPadding = 12.dp
            )
        }
    }
}

@Preview
@Composable
fun CategoryScreenRoute(viewModel: TodoViewModel = hiltViewModel()) {
    val categoryList by viewModel.categoryList.collectAsState()
    val inviteGroupCategoryList by viewModel.inviteGroupCategoryList.collectAsState()
    PomoroDoTheme {
        Scaffold(
            modifier = Modifier,
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
                    titleTextId = R.string.title_category,
                    iconString = IC_ADD_CATEGORY,
                    descriptionId = R.string.content_ic_ok,
                    onClickedListener = {},
                    onBackClickedListener = {}
                )
                CategoryScreen(
                    inviteGroupCategoryList = inviteGroupCategoryList,
                    normalCategoryList = categoryList.filter { it.groupNumber == 0 },
                    groupCategoryList = categoryList.filter { it.groupNumber > 0 },
                )
            }
        }
    }
}
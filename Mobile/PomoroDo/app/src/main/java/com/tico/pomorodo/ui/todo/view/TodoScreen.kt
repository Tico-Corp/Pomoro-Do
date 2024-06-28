package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.TodoState
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.ui.AppState
import com.tico.pomorodo.ui.common.view.BottomBar
import com.tico.pomorodo.ui.common.view.TodoState
import com.tico.pomorodo.ui.common.view.addFocusCleaner
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.todo.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    selectedProfileIndex: Int,
    userList: List<User>,
    categoryList: List<Category>,
    inputText: String,
    selectedCategoryIndex: Int,
    todoMakeVisible: Boolean,
    onGroupClicked: (Int, Int) -> Unit,
    onSelectedProfileIndexChanged: (Int) -> Unit,
    onAddNewTodoItem: () -> Unit,
    onTodoStateChanged: (Int, Int, TodoState) -> Unit,
    onInputTextChanged: (String) -> Unit,
    onSelectedCategoryIndexChanged: (Int) -> Unit,
    onTodoMakeVisible: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        color = PomoroDoTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TodoProfileItems(
                userList = userList,
                selectedIndex = selectedProfileIndex,
                onClicked = onSelectedProfileIndexChanged
            )
            TodoCalendarScreen()
            TotalFocusStatus(2, 3, 4)
            Column(modifier = Modifier.fillMaxHeight()) {
                repeat(categoryList.size) { categoryIndex ->
                    CategoryTag(
                        categoryList[categoryIndex].title,
                        categoryList[categoryIndex].groupNumber,
                        selectedProfileIndex == -1,
                        onAddClicked = {
                            onSelectedCategoryIndexChanged(categoryIndex)
                            onTodoMakeVisible(true)
                        },
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (todoMakeVisible && categoryIndex == selectedCategoryIndex) {
                        TodoMake(
                            callback = {
                                onAddNewTodoItem()
                            },
                            inputText = inputText,
                            onValueChange = { onInputTextChanged(it) }
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        repeat(categoryList[categoryIndex].todoList.size) { itemIndex ->
                            TodoListItem(
                                todoData = categoryList[categoryIndex].todoList[itemIndex],
                                isGroup = categoryList[categoryIndex].groupNumber > 0,
                                onStateChanged = {
                                    onTodoStateChanged(categoryIndex, itemIndex, it)
                                },
                                onMoreInfoEditClicked = {},
                                onMoreInfoDeleteClicked = {},
                                isFriend = selectedProfileIndex != -1,
                                onGroupClicked = { onGroupClicked(categoryIndex, itemIndex) },
                                onLikedClicked = {}
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TodoScreenRoute(
    viewModel: TodoViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    var showGroupBottomSheet by remember { mutableStateOf(false) }
    var selectedCategoryGroupIndex by rememberSaveable { mutableIntStateOf(-1) }
    var selectedGroupItemIndex by remember { mutableIntStateOf(-1) }
    val selectedProfileIndex by viewModel.selectedProfileIndex.collectAsState()
    val userList by viewModel.userList.collectAsState()
    val categoryList by viewModel.categoryList.collectAsState()
    val inputText by viewModel.inputText.collectAsState()
    val selectedCategoryIndex by viewModel.selectedCategoryIndex.collectAsState()
    val todoMakeVisible by viewModel.todoMakeVisible.collectAsState()
    val focusManager = LocalFocusManager.current

    PomoroDoTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager) {
                    viewModel.addNewTodoItem()
                },
            containerColor = PomoroDoTheme.colorScheme.background,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = { BottomBar(appState = AppState(rememberNavController())) }
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
                TopBar(onManageCategoryClicked = {}, onAddCategoryClicked = {})
                if (showGroupBottomSheet) {
                    GroupBottomSheet(
                        title = categoryList[selectedCategoryGroupIndex].todoList[selectedGroupItemIndex].name,
                        sheetState = sheetState,
                        onShowBottomSheetChange = { showGroupBottomSheet = it },
                        completedList = DataSource.userList,
                        incompletedList = DataSource.userList,
                        totalNumber = 5,
                    )
                }
                TodoScreen(
                    selectedProfileIndex = selectedProfileIndex,
                    userList = userList,
                    categoryList = categoryList,
                    inputText = inputText,
                    selectedCategoryIndex = selectedCategoryIndex,
                    todoMakeVisible = todoMakeVisible,
                    onGroupClicked = { categoryIndex, itemIndex ->
                        selectedCategoryGroupIndex = categoryIndex
                        selectedGroupItemIndex = itemIndex
                        showGroupBottomSheet = true
                    },
                    onSelectedProfileIndexChanged = viewModel::setSelectedProfileIndex,
                    onAddNewTodoItem = viewModel::addNewTodoItem,
                    onTodoStateChanged = viewModel::changeTodoState,
                    onInputTextChanged = viewModel::setInputText,
                    onSelectedCategoryIndexChanged = viewModel::setSelectedCategoryIndex,
                    onTodoMakeVisible = viewModel::setTodoMakeVisible
                )
            }
        }
    }
}
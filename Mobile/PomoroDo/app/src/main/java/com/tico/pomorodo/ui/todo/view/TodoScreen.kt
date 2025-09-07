package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.ui.common.view.addFocusCleaner
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.todo.viewmodel.TodoViewModel
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreenRoute(
    isOffline: Boolean,
    viewModel: TodoViewModel = hiltViewModel(),
    navigateToCategory: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToFollowTodoScreen: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState()
    var showGroupBottomSheet by remember { mutableStateOf(false) }
    val categoryWithTodoItemList by viewModel.categoryWithTodoItemList.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val monthlyLikedNumber by viewModel.monthlyLikedNumber.collectAsState()
    val monthlyFullFocusNumber by viewModel.monthlyFullFocusNumber.collectAsState()
    val monthlyAllCheckedNumber by viewModel.monthlyAllCheckedNumber.collectAsState()
    val calendarDates by viewModel.calendarDates.collectAsState()

    var newTodoItemTitle by remember { mutableStateOf("") }
    var updatedTodoItemTitle by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(-1) }
    var selectedTodoItemIndex by remember { mutableIntStateOf(-1) }
    var selectedTodoItem: TodoData? by remember { mutableStateOf(null) }
    var isNewTodoMakeVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .addFocusCleaner(focusManager) {
                if (isNewTodoMakeVisible) {
                    viewModel.addNewTodoItem(newTodoItemTitle, selectedCategoryIndex)

                    isNewTodoMakeVisible = false
                    newTodoItemTitle = ""
                    selectedCategoryIndex = -1
                } else {
                    if (selectedCategoryIndex >= 0 && selectedTodoItemIndex >= 0) {
                        viewModel.updateTodoItem(
                            selectedCategoryIndex,
                            selectedTodoItemIndex,
                            updatedTodoItemTitle
                        )
                    }

                    selectedCategoryIndex = -1
                    selectedTodoItemIndex = -1
                    updatedTodoItemTitle = ""
                }
            },
        color = PomoroDoTheme.colorScheme.background,
    ) {
        Column {
            TopBar(
                onManageCategoryClicked = navigateToCategory,
                onAddCategoryClicked = navigateToAddCategory
            )
            if (showGroupBottomSheet) {
                selectedTodoItem?.let { todoItem ->
                    GroupBottomSheet(
                        title = todoItem.title,
                        sheetState = sheetState,
                        onShowBottomSheetChange = { showGroupBottomSheet = it },
                        completedList = todoItem.completedList ?: listOf(),
                        incompletedList = todoItem.incompletedList ?: listOf(),
                        totalNumber = (todoItem.completedList?.size ?: 0)
                                + (todoItem.incompletedList?.size ?: 0),
                        isClicked = !isOffline,
                        onClicked = { userId ->
                            showGroupBottomSheet = false
                            navigateToFollowTodoScreen(userId)
                        }
                    )
                }
            }
            TodoScreen(
                selectedDate = selectedDate,
                onDateSelected = viewModel::setSelectedDate,
                monthlyLikedNumber = monthlyLikedNumber,
                monthlyFullFocusNumber = monthlyFullFocusNumber,
                monthlyAllCheckedNumber = monthlyAllCheckedNumber,
                categoryWithTodoItemList = categoryWithTodoItemList,
                isNewTodoMakeVisible = isNewTodoMakeVisible,
                newTodoItemTitle = newTodoItemTitle,
                selectedCategoryIndex = selectedCategoryIndex,
                selectedTodoItemIndex = selectedTodoItemIndex,
                onGroupIconClicked = { todoItem ->
                    selectedTodoItem = todoItem
                    showGroupBottomSheet = true
                },
                onAddNewTodoItem = {
                    viewModel.addNewTodoItem(
                        newTodoItemTitle,
                        selectedCategoryIndex
                    )
                    isNewTodoMakeVisible = false
                    newTodoItemTitle = ""
                    selectedCategoryIndex = -1
                },
                onTodoStateChanged = viewModel::updateTodoState,
                onChangedNewTodoItemTitle = { newTodoItemTitle = it },
                onClickedCategoryTag = { categoryIndex ->
                    isNewTodoMakeVisible = true
                    selectedCategoryIndex = categoryIndex
                    newTodoItemTitle = ""
                },
                onHistoryButtonClicked = navigateToHistory,
                onUpdateTodoItem = {
                    if (selectedCategoryIndex >= 0 && selectedTodoItemIndex >= 0) {
                        viewModel.updateTodoItem(
                            selectedCategoryIndex,
                            selectedTodoItemIndex,
                            updatedTodoItemTitle
                        )
                    }
                    selectedCategoryIndex = -1
                    selectedTodoItemIndex = -1
                    updatedTodoItemTitle = ""
                },
                onMoreInfoEditClicked = { categoryIndex, todoItemIndex ->
                    selectedTodoItemIndex = todoItemIndex
                    selectedCategoryIndex = categoryIndex
                    isNewTodoMakeVisible = false
                },
                onMoreInfoDeleteClicked = viewModel::deleteTodoItem,
                updatedTodoItemTitle = updatedTodoItemTitle,
                onUpdatedTodoItemTitle = { updatedTodoItemTitle = it },
                calendarDates = calendarDates,
                isOffline = isOffline
            )
        }
    }
}

@Composable
fun TodoScreen(
    isOffline: Boolean,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    monthlyLikedNumber: Int,
    monthlyFullFocusNumber: Int,
    monthlyAllCheckedNumber: Int,
    categoryWithTodoItemList: List<CategoryWithTodoItem>,
    isNewTodoMakeVisible: Boolean,
    newTodoItemTitle: String,
    selectedTodoItemIndex: Int,
    selectedCategoryIndex: Int,
    updatedTodoItemTitle: String,
    onUpdatedTodoItemTitle: (String) -> Unit,
    onGroupIconClicked: (TodoData) -> Unit,
    onAddNewTodoItem: () -> Unit,
    onTodoStateChanged: (TodoData) -> Unit,
    onChangedNewTodoItemTitle: (String) -> Unit,
    onMoreInfoEditClicked: (Int, Int) -> Unit,
    onMoreInfoDeleteClicked: (Int) -> Unit,
    onClickedCategoryTag: (Int) -> Unit,
    onHistoryButtonClicked: () -> Unit,
    onUpdateTodoItem: () -> Unit,
    calendarDates: List<CalendarDate>
) {
    Column(
        modifier = Modifier.padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TodoCalendarScreen(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            monthlyFullFocusNumber = monthlyFullFocusNumber,
            monthlyAllCheckedNumber = monthlyAllCheckedNumber,
            monthlyLikedNumber = monthlyLikedNumber,
            calendarDates = calendarDates
        )
        TotalFocusStatus(
            2,
            3,
            4,
            isFriend = false,
            onHistoryButtonClicked = onHistoryButtonClicked
        )
        CategorySection(
            isFriend = false,
            categoryWithTodoItemList = categoryWithTodoItemList,
            onClickedCategoryTag = onClickedCategoryTag,
            isNewTodoMakeVisible = isNewTodoMakeVisible,
            newTodoItemTitle = newTodoItemTitle,
            selectedTodoItemIndex = selectedTodoItemIndex,
            selectedCategoryIndex = selectedCategoryIndex,
            updatedTodoItemTitle = updatedTodoItemTitle,
            onUpdatedTodoItemTitle = onUpdatedTodoItemTitle,
            onAddNewTodoItem = onAddNewTodoItem,
            onMoreInfoEditClicked = onMoreInfoEditClicked,
            onMoreInfoDeleteClicked = onMoreInfoDeleteClicked,
            onUpdateTodoItem = onUpdateTodoItem,
            onChangedNewTodoItemTitle = onChangedNewTodoItemTitle,
            onTodoStateChanged = onTodoStateChanged,
            onGroupIconClicked = onGroupIconClicked,
            isOffline = isOffline
        )
    }
}

@Composable
fun CategorySection(
    isFriend: Boolean,
    categoryWithTodoItemList: List<CategoryWithTodoItem>,
    onClickedCategoryTag: (Int) -> Unit = {},
    isNewTodoMakeVisible: Boolean = false,
    newTodoItemTitle: String = "",
    selectedTodoItemIndex: Int = -1,
    selectedCategoryIndex: Int = -1,
    updatedTodoItemTitle: String = "",
    onUpdatedTodoItemTitle: (String) -> Unit = {},
    onAddNewTodoItem: () -> Unit = {},
    onMoreInfoEditClicked: (Int, Int) -> Unit = { _, _ -> },
    onMoreInfoDeleteClicked: (Int) -> Unit = {},
    onLikedIconClicked: (Int) -> Unit = {},
    onUpdateTodoItem: () -> Unit = {},
    onChangedNewTodoItemTitle: (String) -> Unit = {},
    onTodoStateChanged: (TodoData) -> Unit = {},
    onGroupIconClicked: (TodoData) -> Unit,
    isOffline: Boolean
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        categoryWithTodoItemList.forEachIndexed { categoryIndex, category ->
            CategoryWithTodoItems(
                isOffline = isOffline,
                categoryWithTodoItem = category,
                isNewTodoMakeVisible = isNewTodoMakeVisible && categoryIndex == selectedCategoryIndex,
                onClickedCategoryTag = { onClickedCategoryTag(categoryIndex) },
                onChangedNewTodoItemTitle = onChangedNewTodoItemTitle,
                newTodoItemTitle = newTodoItemTitle,
                onAddNewTodoItem = onAddNewTodoItem,
                onTodoStateChanged = onTodoStateChanged,
                onGroupIconClicked = onGroupIconClicked,
                onUpdateTodoItem = onUpdateTodoItem,
                selectedTodoItemIndex = selectedTodoItemIndex,
                onMoreInfoEditClicked = { onMoreInfoEditClicked(categoryIndex, it) },
                onMoreInfoDeleteClicked = onMoreInfoDeleteClicked,
                updatedTodoItemTitle = updatedTodoItemTitle,
                onUpdatedTodoItemTitle = onUpdatedTodoItemTitle,
                isEditMode = !isNewTodoMakeVisible && categoryIndex == selectedCategoryIndex,
                isFriend = isFriend,
                onLikedIconClicked = onLikedIconClicked,
                isGroupCategory = category.type == CategoryType.GROUP
            )
        }
    }
}

@Composable
fun CategoryWithTodoItems(
    isFriend: Boolean,
    categoryWithTodoItem: CategoryWithTodoItem,
    isNewTodoMakeVisible: Boolean = false,
    selectedTodoItemIndex: Int,
    isEditMode: Boolean,
    newTodoItemTitle: String,
    updatedTodoItemTitle: String,
    onUpdatedTodoItemTitle: (String) -> Unit,
    onClickedCategoryTag: () -> Unit,
    onChangedNewTodoItemTitle: (String) -> Unit,
    onMoreInfoEditClicked: (Int) -> Unit,
    onMoreInfoDeleteClicked: (Int) -> Unit,
    onLikedIconClicked: (Int) -> Unit,
    onAddNewTodoItem: () -> Unit,
    onTodoStateChanged: (TodoData) -> Unit,
    onGroupIconClicked: (TodoData) -> Unit,
    onUpdateTodoItem: () -> Unit,
    isOffline: Boolean,
    isGroupCategory: Boolean
) {
    CategoryTag(
        title = categoryWithTodoItem.title,
        groupMemberCount = categoryWithTodoItem.groupMemberCount,
        onAddClicked = onClickedCategoryTag,
        isAddButton = (!isFriend && !isOffline) || (isOffline && !isGroupCategory),
        enabled = (!isFriend && !isOffline) || (isOffline && !isGroupCategory)
    )
    Spacer(modifier = Modifier.height(10.dp))
    if (isNewTodoMakeVisible) {
        TodoMake(
            callback = onAddNewTodoItem,
            inputText = newTodoItemTitle,
            onValueChange = { onChangedNewTodoItemTitle(it) }
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        categoryWithTodoItem.todoList.forEachIndexed { itemIndex, todoData ->
            TodoListItem(
                isOffline = isOffline,
                todoData = todoData,
                isGroupTodo = isGroupCategory,
                onStateChanged = {
                    onTodoStateChanged(todoData)
                },
                onMoreInfoEditClicked = {
                    onMoreInfoEditClicked(itemIndex)
                    onUpdatedTodoItemTitle(todoData.title)
                },
                onMoreInfoDeleteClicked = {
                    onMoreInfoDeleteClicked(todoData.id)
                },
                isFriend = isFriend,
                isEditMode = isEditMode && itemIndex == selectedTodoItemIndex,
                onGroupIconClicked = { onGroupIconClicked(todoData) },
                onLikedIconClicked = { onLikedIconClicked(todoData.id) },
                onUpdateTodoItem = onUpdateTodoItem,
                updatedTodoItemTitle = updatedTodoItemTitle,
                onUpdatedTodoItemTitle = onUpdatedTodoItemTitle,
                isLikedClick = todoData.isLikedClick
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.domain.model.Follow
import com.tico.pomorodo.ui.common.view.CustomTopAppBar
import com.tico.pomorodo.ui.follow.view.FollowItem
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.todo.viewmodel.FollowTodoViewModel
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowTodoScreenRoute(
    viewModel: FollowTodoViewModel = hiltViewModel(),
    navigateToFollowTodoScreen: (Int) -> Unit,
    navigateToBack: () -> Unit,
    isOffline: Boolean
) {
    val sheetState = rememberModalBottomSheetState()
    val user by viewModel.user.collectAsState()
    var showGroupBottomSheet by remember { mutableStateOf(false) }
    val categoryWithTodoItemList by viewModel.categoryWithTodoItemList.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val monthlyLikedNumber by viewModel.monthlyLikedNumber.collectAsState()
    val monthlyFullFocusNumber by viewModel.monthlyFullFocusNumber.collectAsState()
    val monthlyAllCheckedNumber by viewModel.monthlyAllCheckedNumber.collectAsState()
    val calendarDates by viewModel.calendarDates.collectAsState()

    var selectedTodoItem: TodoData? by remember { mutableStateOf(null) }

    user?.let { friendUser ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = PomoroDoTheme.colorScheme.background,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                CustomTopAppBar(onBackClickedListener = navigateToBack)
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
                FollowTodoScreen(
                    user = friendUser,
                    onButtonClicked = viewModel::setFollowing,
                    selectedDate = selectedDate,
                    onDateSelected = viewModel::setSelectedDate,
                    monthlyLikedNumber = monthlyLikedNumber,
                    monthlyFullFocusNumber = monthlyFullFocusNumber,
                    monthlyAllCheckedNumber = monthlyAllCheckedNumber,
                    categoryWithTodoItemList = categoryWithTodoItemList,
                    onGroupIconClicked = { todoItem ->
                        selectedTodoItem = todoItem
                        showGroupBottomSheet = true
                    },
                    onLikedIconClicked = viewModel::updateTodoLiked,
                    calendarDates = calendarDates,
                )
            }
        }
    }
}

@Composable
fun FollowTodoScreen(
    user: Follow,
    onButtonClicked: () -> Unit,
    onGroupIconClicked: (TodoData) -> Unit,
    onLikedIconClicked: (Int) -> Unit,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    monthlyLikedNumber: Int,
    monthlyFullFocusNumber: Int,
    monthlyAllCheckedNumber: Int,
    categoryWithTodoItemList: List<CategoryWithTodoItem>,
    calendarDates: List<CalendarDate>,
) {
    Column(
        modifier = Modifier.padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FollowItem(
            user = user,
            followButtonText = stringResource(id = R.string.title_following),
            unFollowButtonText = stringResource(id = R.string.title_follow),
            followButtonContainerColor = PomoroDoTheme.colorScheme.gray90,
            followButtonContentColor = PomoroDoTheme.colorScheme.gray20,
            unFollowButtonContainerColor = PomoroDoTheme.colorScheme.primaryContainer,
            unFollowButtonContentColor = Color.White,
            onClick = onButtonClicked
        )
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
            isFriend = true
        )
        CategorySection(
            isFriend = true,
            categoryWithTodoItemList = categoryWithTodoItemList,
            onLikedIconClicked = onLikedIconClicked,
            onGroupIconClicked = onGroupIconClicked,
            isOffline = false
        )
    }
}
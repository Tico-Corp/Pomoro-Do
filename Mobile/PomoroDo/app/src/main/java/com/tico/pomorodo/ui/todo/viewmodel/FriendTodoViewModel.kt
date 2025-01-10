package com.tico.pomorodo.ui.todo.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.domain.model.Follow
import com.tico.pomorodo.ui.common.view.toTimeZoneOf5AM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class FriendTodoViewModel @Inject constructor() : ViewModel() {

    private var _categoryWithTodoItemList =
        MutableStateFlow<List<CategoryWithTodoItem>>(emptyList())
    val categoryWithTodoItemList: StateFlow<List<CategoryWithTodoItem>>
        get() = _categoryWithTodoItemList.asStateFlow()

    private var _user = MutableStateFlow<Follow?>(null)
    val user: StateFlow<Follow?>
        get() = _user.asStateFlow()

    private var _selectedDate =
        MutableStateFlow<LocalDate>(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toTimeZoneOf5AM()
        )
    val selectedDate: StateFlow<LocalDate>
        get() = _selectedDate.asStateFlow()

    private var _monthlyLikedNumber = MutableStateFlow<Int>(0)
    val monthlyLikedNumber: StateFlow<Int>
        get() = _monthlyLikedNumber.asStateFlow()

    private var _monthlyFullFocusNumber = MutableStateFlow<Int>(0)
    val monthlyFullFocusNumber: StateFlow<Int>
        get() = _monthlyFullFocusNumber.asStateFlow()

    private var _monthlyAllCheckedNumber = MutableStateFlow<Int>(0)
    val monthlyAllCheckedNumber: StateFlow<Int>
        get() = _monthlyAllCheckedNumber.asStateFlow()

    private var _calendarDates = MutableStateFlow<List<CalendarDate>>(emptyList())
    val calendarDates: StateFlow<List<CalendarDate>>
        get() = _calendarDates.asStateFlow()

    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    init {
        fetchFollowing()
        getCategoryWithTodoItems()
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    private fun fetchFollowing() {
        // TODO: follower user 정보 불러오는 로직 구현
    }

    fun setFollowing() {
        // TODO: follow/following 버튼 클릭 로직 구현
    }

    fun updateTodoLicked(todoId: Int) {
        // TODO: 좋아요 로직 구현
    }

    private fun getCategoryWithTodoItems() {
        // TODO: 친구의 카테고리 별 투두 불러오는 로직
    }
}
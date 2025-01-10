package com.tico.pomorodo.ui.todo.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
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
class TodoViewModel @Inject constructor() : ViewModel() {

    private var _categoryWithTodoItemList =
        MutableStateFlow<List<CategoryWithTodoItem>>(emptyList())
    val categoryWithTodoItemList: StateFlow<List<CategoryWithTodoItem>>
        get() = _categoryWithTodoItemList.asStateFlow()

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
        getCategoryWithTodoItems()
        getCalendarDates()
    }

    private fun getCalendarDates() {
        // TODO: get calendarDate
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    private fun getCategoryWithTodoItems() {
        // TODO: get categoryWithTodo
    }

    fun addNewTodoItem(title: String, categoryIndex: Int) {
        if (validateTodoInput(title)) {
            // TODO: add new todo
        }
    }

    fun updateTodoState(todo: TodoData) {
        val newState = when (todo.status) {
            TodoState.UNCHECKED -> TodoState.CHECKED
            TodoState.CHECKED -> TodoState.GOING
            TodoState.GOING -> TodoState.UNCHECKED
        }
        // TODO: update todo state
    }

    private suspend fun updateCalendarData(categoryWithTodos: List<CategoryWithTodoItem>) {
        // TODO: update calendarDate
    }

    private fun validateTodoInput(inputText: String): Boolean = inputText.isNotBlank()

    fun updateTodoItem(categoryIndex: Int, todoItemIndex: Int, title: String) {
        // TODO: update todo
    }

    fun deleteTodoItem(todoId: Int) {
        // TODO: delete todo
    }
}
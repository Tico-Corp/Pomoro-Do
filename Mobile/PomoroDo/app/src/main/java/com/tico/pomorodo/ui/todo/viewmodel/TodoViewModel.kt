package com.tico.pomorodo.ui.todo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.model.CalendarDate
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.calendar.GetCalendarDateForMonthUseCase
import com.tico.pomorodo.domain.usecase.calendar.InsertCalendarDateForMonthUseCase
import com.tico.pomorodo.domain.usecase.calendar.UpdateCalendarDateForMonthUseCase
import com.tico.pomorodo.domain.usecase.todo.DeleteTodoUseCase
import com.tico.pomorodo.domain.usecase.todo.GetCategoryWithTodoItemsUseCase
import com.tico.pomorodo.domain.usecase.todo.InsertTodoUseCase
import com.tico.pomorodo.domain.usecase.todo.UpdateTodoUseCase
import com.tico.pomorodo.ui.common.view.atEndOfMonth
import com.tico.pomorodo.ui.common.view.atStartOfMonth
import com.tico.pomorodo.ui.common.view.toTimeZoneOf5AM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val getCategoryWithTodoItemsUseCase: GetCategoryWithTodoItemsUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val getCalendarDateForMonthUseCase: GetCalendarDateForMonthUseCase,
    private val insertCalendarDateForMonthUseCase: InsertCalendarDateForMonthUseCase,
    private val updateCalendarDateForMonthUseCase: UpdateCalendarDateForMonthUseCase,
) : ViewModel() {

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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCalendarDates() = viewModelScope.launch {
        _selectedDate.flatMapLatest { date ->
            val startEpochDays = date.atStartOfMonth().toEpochDays()
            val endEpochDays = date.atEndOfMonth().toEpochDays()
            getCalendarDateForMonthUseCase(startEpochDays, endEpochDays)
        }.collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _isLoading.value = true
                }

                is Resource.Success -> {
                    _isLoading.value = false
                    _calendarDates.value = result.data
                }

                is Resource.Failure.Exception -> {
                    Log.e("TodoViewModel", "refreshCalendarDates: ${result.message}")
                }

                is Resource.Failure.Error -> {
                    Log.e(
                        "TodoViewModel",
                        "refreshCalendarDates: ${result.code} ${result.message}"
                    )
                }
            }
        }
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCategoryWithTodoItems() = viewModelScope.launch {
        _selectedDate.flatMapLatest { date ->
            getCategoryWithTodoItemsUseCase(date)
        }.collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _isLoading.value = true
                }

                is Resource.Success -> {
                    _isLoading.value = false
                    _categoryWithTodoItemList.value = result.data
                    updateCalendarData(result.data)
                }

                is Resource.Failure.Exception -> {
                    Log.e("TodoViewModel", "refreshCategoryWithTodoItems: ${result.message}")
                }

                is Resource.Failure.Error -> {
                    Log.e(
                        "TodoViewModel",
                        "refreshCategoryWithTodoItems: ${result.code} ${result.message}"
                    )
                }
            }
        }
    }

    fun addNewTodoItem(title: String, categoryIndex: Int) {
        if (validateTodoInput(title)) {
            val category = categoryWithTodoItemList.value[categoryIndex]
            viewModelScope.launch {
                insertTodoUseCase(
                    title = title,
                    categoryId = category.categoryId,
                    incompletedList = category.groupMember,
                    completedList = listOf(),
                    targetDate = selectedDate.value
                )
            }
        }
    }

    fun updateTodoState(todo: TodoData) {
        val newState = when (todo.status) {
            TodoState.UNCHECKED -> TodoState.CHECKED
            TodoState.CHECKED -> TodoState.GOING
            TodoState.GOING -> TodoState.UNCHECKED
        }
        viewModelScope.launch {
            updateTodoUseCase(todo.copy(status = newState, updatedAt = System.currentTimeMillis()))
        }
    }

    private suspend fun updateCalendarData(categoryWithTodos: List<CategoryWithTodoItem>) {
        val totalTodos = categoryWithTodos.sumOf { it.todoList.size }
        val uncheckedTodos =
            categoryWithTodos.sumOf { it.todoList.count { todo -> todo.status != TodoState.CHECKED } }

        val currentDate = calendarDates.value.find { it.date == selectedDate.value }
        currentDate?.let {
            updateCalendarDateForMonthUseCase(
                it.copy(
                    remainedTodoCount = uncheckedTodos,
                    totalCount = totalTodos
                )
            )
        } ?: insertCalendarDateForMonthUseCase(
            CalendarDate(
                date = selectedDate.value,
                remainedTodoCount = uncheckedTodos,
                totalCount = totalTodos
            )
        )
    }

    private fun validateTodoInput(inputText: String): Boolean = inputText.isNotBlank()

    fun updateTodoItem(categoryIndex: Int, todoItemIndex: Int, title: String) {
        val newTodoData =
            categoryWithTodoItemList.value[categoryIndex].todoList[todoItemIndex].copy(
                title = title,
                updatedAt = System.currentTimeMillis()
            )
        viewModelScope.launch {
            updateTodoUseCase(newTodoData)
        }
    }

    fun deleteTodoItem(todoId: Int) {
        viewModelScope.launch {
            deleteTodoUseCase(todoId)
        }
    }
}
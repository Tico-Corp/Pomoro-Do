package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TodoRepository {
    suspend fun insertTodo(
        title: String,
        categoryId: Int,
        completedList: List<User>?,
        incompletedList: List<User>?,
        targetDate: LocalDate
    )

    suspend fun getCategoryWithTodoItems(selectedDate: LocalDate): Flow<Resource<List<CategoryWithTodoItem>>>
    suspend fun updateTodo(todoData: TodoData)
    suspend fun deleteTodo(id: Int)
}
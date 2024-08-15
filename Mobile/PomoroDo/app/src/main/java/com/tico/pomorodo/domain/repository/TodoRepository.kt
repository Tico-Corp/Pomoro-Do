package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.TodoData
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodo(): Flow<List<TodoData>>
    suspend fun insertTodo(todoData: TodoData)
}
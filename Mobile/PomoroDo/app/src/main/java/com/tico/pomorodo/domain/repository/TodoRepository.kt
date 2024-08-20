package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodo(): Flow<Resource<List<TodoData>>>
    suspend fun insertTodo(todoData: TodoData)
}
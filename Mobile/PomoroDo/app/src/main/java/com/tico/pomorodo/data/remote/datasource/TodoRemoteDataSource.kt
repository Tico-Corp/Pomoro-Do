package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.TodoRequest
import com.tico.pomorodo.data.remote.models.response.TodoResponse

interface TodoRemoteDataSource {
    suspend fun getAllTodo(): List<TodoResponse>

    suspend fun getTodo(id: Int): TodoResponse

    suspend fun insertTodo(todoRequest: TodoRequest): TodoResponse

    suspend fun insertAll(todoRequests: List<TodoRequest>): TodoResponse

    suspend fun updateTodo(todoRequest: TodoRequest): TodoResponse

    suspend fun deleteTodo(id: Int): TodoResponse
}
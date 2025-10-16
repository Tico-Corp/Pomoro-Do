package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.todo.TodoRequest
import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.todo.CategoryWithTodoItemResponse
import com.tico.pomorodo.data.remote.models.response.todo.TodoResponse

interface TodoRemoteDataSource {
    suspend fun insertTodo(todoRequest: TodoRequest): TodoResponse

    suspend fun insertAll(todoRequests: List<TodoRequest>): TodoResponse

    suspend fun updateTodo(todoRequest: TodoRequest): TodoResponse

    suspend fun deleteTodo(id: Int): TodoResponse

    suspend fun getCategoryWithTodoItems(): BaseResponse<List<CategoryWithTodoItemResponse>>
}
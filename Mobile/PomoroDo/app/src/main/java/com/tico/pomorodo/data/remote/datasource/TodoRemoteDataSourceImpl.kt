package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.todo.TodoRequest
import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.todo.CategoryWithTodoItemResponse
import com.tico.pomorodo.data.remote.models.response.todo.TodoResponse
import com.tico.pomorodo.data.remote.service.TodoApiService
import javax.inject.Inject

class TodoRemoteDataSourceImpl @Inject constructor(private val todoApiService: TodoApiService) :
    TodoRemoteDataSource {
    override suspend fun insertTodo(todoRequest: TodoRequest): TodoResponse =
        todoApiService.insertTodo(todoRequest)

    override suspend fun insertAll(todoRequests: List<TodoRequest>): TodoResponse =
        todoApiService.insertAll(todoRequests)

    override suspend fun updateTodo(todoRequest: TodoRequest): TodoResponse =
        todoApiService.updateTodo(todoRequest)

    override suspend fun deleteTodo(id: Int): TodoResponse = todoApiService.deleteTodo(id)

    override suspend fun getCategoryWithTodoItems(): BaseResponse<List<CategoryWithTodoItemResponse>> =
        todoApiService.getCategoryWithTodoItems()
}
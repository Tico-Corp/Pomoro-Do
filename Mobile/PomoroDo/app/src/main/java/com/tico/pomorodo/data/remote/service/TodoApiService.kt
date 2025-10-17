package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.request.todo.TodoRequest
import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.todo.CategoryWithTodoItemResponse
import com.tico.pomorodo.data.remote.models.response.todo.TodoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApiService {
    @POST
    suspend fun insertTodo(@Body todoRequest: TodoRequest): TodoResponse

    @POST
    suspend fun insertAll(todoRequests: List<TodoRequest>): TodoResponse

    @POST
    suspend fun updateTodo(todoRequest: TodoRequest): TodoResponse

    @DELETE
    suspend fun deleteTodo(id: Int): TodoResponse

    @GET("")
    suspend fun getCategoryWithTodoItems(): BaseResponse<List<CategoryWithTodoItemResponse>>
}
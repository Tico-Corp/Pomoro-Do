package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.request.TodoRequest
import com.tico.pomorodo.data.remote.models.response.TodoResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApiService {
    @GET("")
    suspend fun getAllTodo(): List<TodoResponse>

    @GET("")
    suspend fun getTodo(id: Int): TodoResponse

    @POST
    suspend fun insertTodo(@Body todoRequest: TodoRequest): TodoResponse

    @POST
    suspend fun insertAll(todoRequests: List<TodoRequest>): TodoResponse

    @POST
    suspend fun updateTodo(todoRequest: TodoRequest): TodoResponse

    @DELETE
    suspend fun deleteTodo(id: Int): TodoResponse
}
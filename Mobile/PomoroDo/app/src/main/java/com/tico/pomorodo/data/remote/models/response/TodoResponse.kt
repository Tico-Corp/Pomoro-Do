package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
import kotlinx.serialization.Serializable

@Serializable
data class TodoResponse(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val status: TodoState,
    val createdAt: Long,
    val updatedAt: Long,
    val likes: Int
)

fun TodoResponse.toTodoData() = TodoData(
    id = id,
    title = title,
    categoryId = categoryId,
    status = status,
    likes = likes,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun TodoResponse.toTodoEntity() = TodoEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    status = status,
    likes = likes,
    createdAt = createdAt,
    updatedAt = updatedAt
)
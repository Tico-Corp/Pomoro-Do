package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.remote.models.request.TodoRequest

data class TodoData(
    val id: Int,
    val title: String,
    var status: TodoState,
    var categoryId: Int,
    val completeGroupNumber: Int? = null,
    val likes: Int = 0,
    val createdAt: Long,
    val updatedAt: Long,
)

enum class TodoState {
    UNCHECKED, GOING, CHECKED
}

fun TodoData.toTodoEntity() = TodoEntity(
    id = id,
    title = title,
    status = status,
    likes = likes,
    categoryId = categoryId,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun TodoData.toTodoRequest()= TodoRequest(
    id = id,
    title = title,
    status = status,
    likes = likes,
    categoryId = categoryId,
    createdAt = createdAt,
    updatedAt = updatedAt
)
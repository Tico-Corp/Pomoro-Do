package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class TodoResponse(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val status: TodoState,
    val createdAt: Long,
    val updatedAt: Long,
    val likes: Int,
    val targetDate: LocalDate,
    val completedList: List<UserResponse>,
    val incompletedList: List<UserResponse>,
)

fun TodoResponse.toTodoData() = TodoData(
    id = id,
    title = title,
    categoryId = categoryId,
    status = status,
    likes = likes,
    completedList = completedList.map(UserResponse::toUser),
    incompletedList = incompletedList.map(UserResponse::toUser),
    createdAt = createdAt,
    updatedAt = updatedAt,
    targetDate = targetDate
)

fun TodoResponse.toTodoEntity() = TodoEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    status = status,
    likes = likes,
    targetDate = targetDate,
    createdAt = createdAt,
    updatedAt = updatedAt
)
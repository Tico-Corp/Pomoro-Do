package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.remote.models.request.TodoRequest
import kotlinx.datetime.LocalDate

data class TodoData(
    val id: Int = 0,
    val title: String,
    var status: TodoState,
    var categoryId: Int,
    val completedList: List<User>? = null,
    val incompletedList: List<User>? = null,
    val likes: Int = 0,
    val targetDate: LocalDate,
    val createdAt: Long,
    val updatedAt: Long,
    val isLikedClick: Boolean = true,
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
    updatedAt = updatedAt,
    targetDate = targetDate,
)

fun TodoData.toTodoRequest() = TodoRequest(
    id = id,
    title = title,
    status = status,
    likes = likes,
    categoryId = categoryId,
)
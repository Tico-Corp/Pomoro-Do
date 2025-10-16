package com.tico.pomorodo.data.remote.models.response.todo

import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.remote.models.response.user.UserResponse
import com.tico.pomorodo.data.remote.models.response.user.toUser
import kotlinx.serialization.Serializable

@Serializable
data class CategoryWithTodoItemResponse(
    val categoryId: Int,
    val title: String,
    val type: CategoryType,
    val todoList: List<TodoResponse>,
    val openSettings: OpenSettings,
    val totalMemberCount: Int,
    val groupMembers: List<UserResponse>
)

fun CategoryWithTodoItemResponse.toCategoryWithTodoItem() = CategoryWithTodoItem(
    categoryId = categoryId,
    title = title,
    type = type,
    todoList = todoList.map(TodoResponse::toTodoData),
    openSettings = openSettings,
    totalMemberCount = totalMemberCount,
    groupMembers = groupMembers.map(UserResponse::toUser)
)
package com.tico.pomorodo.data.remote.models.request

import com.tico.pomorodo.data.model.TodoState
import kotlinx.serialization.Serializable

@Serializable
data class TodoRequest(
    val id: Int,
    val title: String,
    var status: TodoState,
    var categoryId: Int,
    val likes: Int = 0,
    val createdAt: Long,
    val updatedAt: Long,
)
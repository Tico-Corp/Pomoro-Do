package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val categoryId: Int,
    val name: String,
    val type: CategoryType,
    val ownerNickname: String,
    val ownerFlag: Boolean,
    val visibility: OpenSettings,
    val members: List<UserResponse>,
    val totalMembers: Int,
)

fun CategoryResponse.toCategory() = Category(
    id = categoryId,
    title = name,
    type = type,
    openSettings = visibility,
    ownerNickname = ownerNickname,
    ownerFlag = ownerFlag,
    groupMembers = members.map(UserResponse::toUser),
    totalMembers = totalMembers
)
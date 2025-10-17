package com.tico.pomorodo.data.remote.models.response.category

import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DecideCategoryInvitationResponse(
    val categoryId: Int,
    val name: String,
    @SerialName("totalMembers")
    val totalMemberCount: Int = 0
)

fun DecideCategoryInvitationResponse.toCategoryEntity(
    type: CategoryType,
    openSettings: OpenSettings
) = CategoryEntity(
    id = categoryId,
    title = name,
    type = type,
    totalMemberCount = totalMemberCount,
    openSettings = openSettings
)
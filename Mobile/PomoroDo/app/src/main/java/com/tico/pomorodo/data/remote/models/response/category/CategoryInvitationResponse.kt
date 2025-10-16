package com.tico.pomorodo.data.remote.models.response.category

import com.tico.pomorodo.data.model.CategoryInvitation
import kotlinx.serialization.Serializable

@Serializable
data class CategoryInvitationResponse(
    val categoryInvitationId: Int,
    val categoryName: String,
    val ownerNickname: String
)

fun CategoryInvitationResponse.toCategoryInvitation() = CategoryInvitation(
    categoryInvitationId = categoryInvitationId,
    categoryName = categoryName,
    ownerNickname = ownerNickname
)
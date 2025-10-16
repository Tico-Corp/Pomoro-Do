package com.tico.pomorodo.data.remote.models.request.category

import com.tico.pomorodo.data.model.Decision
import kotlinx.serialization.Serializable

@Serializable
data class CategoryInvitationRequest(
    val decision: Decision
)
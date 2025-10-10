package com.tico.pomorodo.data.remote.models.request

import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.Decision
import com.tico.pomorodo.data.model.DeletionOption
import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequest(
    val startDate: String,
    @SerialName("name")
    val title: String,
    val type: CategoryType,
    @SerialName("visibility")
    val openSettings: OpenSettings,
    val memberIds: List<Int>?
)

@Serializable
data class CategoryUpdateRequest(
    @SerialName("name")
    val title: String,
    @SerialName("visibility")
    val openSettings: OpenSettings
)

@Serializable
data class CategoryDeleteRequest(
    val deletionOption: DeletionOption
)

@Serializable
data class CategoryInvitationRequest(
    val decision: Decision
)
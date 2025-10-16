package com.tico.pomorodo.data.remote.models.response.category

import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.SyncState
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsertUpdateCategoryResponse(
    val categoryId: Int,
    val name: String,
    val type: CategoryType,
    val visibility: OpenSettings,
    @SerialName("totalMembers")
    val totalMemberCount: Int = 0
)

fun InsertUpdateCategoryResponse.toCategoryEntity(syncState: SyncState = SyncState.SYNCED) =
    CategoryEntity(
        id = categoryId,
        title = name,
        type = type,
        totalMemberCount = totalMemberCount,
        openSettings = visibility,
        syncState = syncState
    )
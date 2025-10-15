package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.SyncState
import com.tico.pomorodo.data.remote.models.request.CategoryUpdateRequest

data class Category(
    val id: Int = 0,
    val title: String,
    val type: CategoryType,
    val openSettings: OpenSettings = OpenSettings.PUBLIC,
    val ownerNickname: String? = null,
    val ownerFlag: Boolean,
    val groupMembers: List<User>? = null,
    val totalMemberCount: Int
)

enum class CategoryType {
    PERSONAL, GROUP
}

fun Category.toCategoryEntity(syncState: SyncState = SyncState.SYNCED) = CategoryEntity(
    id = id,
    title = title,
    type = type,
    openSettings = openSettings,
    ownerNickname = ownerNickname,
    ownerFlag = ownerFlag,
    groupMember = groupMembers?.map(User::toUserEntity),
    totalMemberCount = totalMemberCount,
    syncState = syncState
)

fun Category.toCategoryUpdateRequest() = CategoryUpdateRequest(
    title = title,
    openSettings = openSettings
)
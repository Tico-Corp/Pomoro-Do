package com.tico.pomorodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.Serializable

@Entity(tableName = "category_table")
@Serializable
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val type: CategoryType,
    val openSettings: OpenSettings,
    val groupReader: String?,
    val isGroupReader: Boolean?,
    val groupMemberCount: Int = 0,
    val groupMember: List<UserEntity>?
)

fun CategoryEntity.toCategory() =
    Category(
        id = id,
        title = title,
        type = type,
        openSettings = openSettings,
        groupReader = groupReader,
        isGroupReader = isGroupReader,
        groupMemberCount = groupMemberCount,
        groupMember = groupMember?.map { it.toUser() }
    )
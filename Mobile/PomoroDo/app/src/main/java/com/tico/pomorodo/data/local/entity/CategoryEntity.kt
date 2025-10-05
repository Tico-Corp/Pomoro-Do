package com.tico.pomorodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.CategoryList
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
    val ownerNickname: String? = null,
    val ownerFlag: Boolean = true,
    val groupMember: List<UserEntity>? = null,
    val totalMembers: Int = 0
)

fun CategoryEntity.toCategory() = Category(
    id = id,
    title = title,
    type = type,
    openSettings = openSettings,
    ownerNickname = ownerNickname,
    ownerFlag = ownerFlag,
    groupMembers = groupMember?.map { it.toUser() },
    totalMembers = totalMembers
)

fun CategoryEntity.toCategoryList() = CategoryList(
    categoryId = id,
    categoryName = title,
    totalMembers = totalMembers
)
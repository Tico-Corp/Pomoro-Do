package com.tico.pomorodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val type: CategoryType,
    val openSettings: OpenSettings = OpenSettings.FULL
)

fun CategoryEntity.toCategory() =
    Category(id = id, title = title, type = type, openSettings = openSettings)
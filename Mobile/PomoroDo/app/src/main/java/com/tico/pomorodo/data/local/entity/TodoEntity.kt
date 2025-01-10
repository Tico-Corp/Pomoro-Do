package com.tico.pomorodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Entity(tableName = "todo_table")
@Serializable
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val title: String,
    val status: TodoState = TodoState.UNCHECKED,
    @ColumnInfo(name = "target_date")
    val targetDate: LocalDate,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),
    val likes: Int = 0
)

fun TodoEntity.toTodoData() = TodoData(
    id = id,
    categoryId = categoryId,
    title = title,
    status = status,
    likes = likes,
    createdAt = createdAt,
    updatedAt = updatedAt,
    targetDate = targetDate
)
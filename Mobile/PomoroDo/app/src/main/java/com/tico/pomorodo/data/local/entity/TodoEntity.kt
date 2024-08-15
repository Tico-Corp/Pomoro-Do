package com.tico.pomorodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val title: String,
    val status: TodoState,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    val likes: Int
)

fun TodoEntity.toTodoData() = TodoData(
    id = id,
    categoryId = categoryId,
    title = title,
    status = status,
    createdAt = createdAt,
    updatedAt = updatedAt,
    likes = likes
)
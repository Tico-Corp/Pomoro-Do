package com.tico.pomorodo.data.local.datasource.todo

import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TodoLocalDataSource {
    suspend fun insert(todoEntity: TodoEntity)

    suspend fun insertAll(todoEntities: List<TodoEntity>)

    suspend fun update(todoEntity: TodoEntity)

    suspend fun delete(id: Int)

    suspend fun getCategoryWithTodoItems(selectedDate: LocalDate): Flow<Map<CategoryEntity, List<TodoEntity>>>
}
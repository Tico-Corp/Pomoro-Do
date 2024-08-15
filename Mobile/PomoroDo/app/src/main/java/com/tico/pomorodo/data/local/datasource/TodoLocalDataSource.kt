package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoLocalDataSource {
    suspend fun getAllTodo(): Flow<List<TodoEntity>>

    suspend fun getTodo(id: Int): Flow<TodoEntity>

    suspend fun insert(todoEntity: TodoEntity)

    suspend fun insertAll(todoEntities: List<TodoEntity>)

    suspend fun update(todoEntity: TodoEntity)

    suspend fun delete(id: Int)
}
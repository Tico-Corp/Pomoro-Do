package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoLocalDataSourceImpl @Inject constructor(private val todoDao: TodoDao) :
    TodoLocalDataSource {
    override suspend fun getAllTodo(): Flow<List<TodoEntity>> = todoDao.getAllTodo()

    override suspend fun getTodo(id: Int): Flow<TodoEntity> = todoDao.getTodo(id)

    override suspend fun insert(todoEntity: TodoEntity) = todoDao.insert(todoEntity)

    override suspend fun insertAll(todoEntities: List<TodoEntity>) = todoDao.insertAll(todoEntities)

    override suspend fun update(todoEntity: TodoEntity) = todoDao.update(todoEntity)

    override suspend fun delete(id: Int) = todoDao.deleteTodo(id)
}
package com.tico.pomorodo.data.local.datasource.todo

import com.tico.pomorodo.data.local.dao.TodoDao
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class TodoLocalDataSourceImpl @Inject constructor(private val todoDao: TodoDao) :
    TodoLocalDataSource {
    override suspend fun insert(todoEntity: TodoEntity) = todoDao.insert(todoEntity)

    override suspend fun insertAll(todoEntities: List<TodoEntity>) = todoDao.insertAll(todoEntities)

    override suspend fun update(todoEntity: TodoEntity) = todoDao.update(todoEntity)

    override suspend fun delete(id: Int) = todoDao.deleteTodo(id)

    override suspend fun getCategoryWithTodoItems(selectedDate: LocalDate): Flow<Map<CategoryEntity, List<TodoEntity>>> =
        todoDao.getCategoryWithTodoItems(selectedDate)
}
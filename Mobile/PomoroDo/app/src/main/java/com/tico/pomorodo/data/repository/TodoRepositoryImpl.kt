package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.datasource.todo.TodoLocalDataSource
import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.local.entity.toTodoData
import com.tico.pomorodo.data.local.entity.toUser
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.data.model.toTodoEntity
import com.tico.pomorodo.data.remote.datasource.TodoRemoteDataSource
import com.tico.pomorodo.data.remote.models.response.CategoryWithTodoItemResponse
import com.tico.pomorodo.data.remote.models.response.toCategoryWithTodoItem
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoLocalDataSource: TodoLocalDataSource,
    private val todoRemoteDataSource: TodoRemoteDataSource,
    private val networkHelper: NetworkHelper
) : TodoRepository {

    override suspend fun insertTodo(
        title: String,
        categoryId: Int,
        completedList: List<User>?,
        incompletedList: List<User>?,
        targetDate: LocalDate
    ) {
        val todoEntity = TodoEntity(
            title = title,
            categoryId = categoryId,
            targetDate = targetDate
        )
        if (networkHelper.isNetworkConnected()) {
        } else {
            todoLocalDataSource.insert(todoEntity)
        }
    }

    override suspend fun getCategoryWithTodoItems(selectedDate: LocalDate): Flow<Resource<List<CategoryWithTodoItem>>> =
        flow {
            emit(Resource.Loading)
            if (networkHelper.isNetworkConnected()) {
                val data = wrapToResource(Dispatchers.IO) {
                    val remoteCategoryWithTodoItems =
                        todoRemoteDataSource.getCategoryWithTodoItems()
                    remoteCategoryWithTodoItems.data.map(CategoryWithTodoItemResponse::toCategoryWithTodoItem)
                }
                emit(data)
            } else {
                val result = todoLocalDataSource.getCategoryWithTodoItems(selectedDate)
                    .map { categoryWithTodoPair ->
                        categoryWithTodoPair.map { (categoryEntity, todoListEntity) ->
                            CategoryWithTodoItem(
                                categoryId = categoryEntity.id,
                                title = categoryEntity.title,
                                openSettings = categoryEntity.openSettings,
                                type = categoryEntity.type,
                                groupMembers = categoryEntity.groupMember?.map { it.toUser() },
                                todoList = todoListEntity.map(TodoEntity::toTodoData),
                                totalMemberCount = categoryEntity.totalMemberCount
                            )
                        }.let { wrapToResource(Dispatchers.IO) { it } }
                    }
                emitAll(result)
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun updateTodo(todoData: TodoData) {
        val todoEntity = todoData.toTodoEntity()
        if (networkHelper.isNetworkConnected()) {
        } else {
            todoLocalDataSource.update(todoEntity)
        }
    }

    override suspend fun deleteTodo(id: Int) {
        if (networkHelper.isNetworkConnected()) {
        } else {
            todoLocalDataSource.delete(id)
        }
    }
}
package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.data.local.datasource.TodoLocalDataSource
import com.tico.pomorodo.data.local.entity.toTodoData
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.toTodoEntity
import com.tico.pomorodo.data.model.toTodoRequest
import com.tico.pomorodo.data.remote.datasource.TodoRemoteDataSource
import com.tico.pomorodo.data.remote.models.response.toTodoData
import com.tico.pomorodo.data.remote.models.response.toTodoEntity
import com.tico.pomorodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoLocalDataSource: TodoLocalDataSource,
    private val todoRemoteDataSource: TodoRemoteDataSource,
    private val networkHelper: NetworkHelper
) : TodoRepository {
    override suspend fun getAllTodo(): Flow<List<TodoData>> = flow {
        if (networkHelper.isNetworkConnected()) {
            try {
                val remoteAllTodo = todoRemoteDataSource.getAllTodo()
                todoLocalDataSource.insertAll(remoteAllTodo.map { it.toTodoEntity() })
                emit(remoteAllTodo.map { it.toTodoData() })
            } catch (e: Exception) {
                emitAll(
                    todoLocalDataSource.getAllTodo()
                        .map { it.map { entity -> entity.toTodoData() } })
            }
        } else {
            emitAll(
                todoLocalDataSource.getAllTodo().map { it.map { entity -> entity.toTodoData() } })
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertTodo(todoData: TodoData) {
        if (networkHelper.isNetworkConnected()) {
            todoRemoteDataSource.insertTodo(todoData.toTodoRequest())
            todoLocalDataSource.insert(todoData.toTodoEntity())
        } else {
            todoLocalDataSource.insert(todoData.toTodoEntity())
        }
    }
}
package com.tico.pomorodo.domain.usecase.todo

import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(): Flow<Resource<List<TodoData>>> = withContext(Dispatchers.IO) {
        todoRepository.getAllTodo()
    }
}
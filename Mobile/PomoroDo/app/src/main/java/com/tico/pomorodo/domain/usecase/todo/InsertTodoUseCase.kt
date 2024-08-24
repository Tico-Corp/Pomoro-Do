package com.tico.pomorodo.domain.usecase.todo

import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(todoData: TodoData) =
        withContext(Dispatchers.IO) { todoRepository.insertTodo(todoData) }
}
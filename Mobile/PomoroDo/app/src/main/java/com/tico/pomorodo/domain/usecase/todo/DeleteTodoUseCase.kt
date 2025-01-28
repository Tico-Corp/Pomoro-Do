package com.tico.pomorodo.domain.usecase.todo

import com.tico.pomorodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(id: Int) = withContext(Dispatchers.IO) {
        todoRepository.deleteTodo(id)
    }
}